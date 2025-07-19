package com.example.bustract.Driver

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bustract.Responses.Model.BusSchedule
import com.example.bustract.Responses.Model.UpdateTracking
import com.example.bustract.Responses.ReTrofit
import com.example.bustract.databinding.ActivityUpdateBusTrackingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class UpdateBusTrackingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBusTrackingBinding
    private val busData = mutableListOf<BusSchedule>()
    private var fetchJob: Job? = null
    private var loadJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBusTrackingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeUI()
        setupListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancel ongoing coroutines when activity is destroyed
        fetchJob?.cancel()
        loadJob?.cancel()
    }

    private fun initializeUI() {
        val busNumber = intent.getStringExtra("vvv").orEmpty()
        binding.busNumberInput.setText(busNumber)

        if (busNumber.isNotEmpty()) {
            fetchBusData(busNumber)
        } else {
            showToast("No bus number provided")
        }

        loadBusData()
    }

    private fun setupListeners() {
        // Add any click listeners or UI interactions here if needed
    }

    private fun loadBusData() {
        loadJob = CoroutineScope(Dispatchers.Main).launch {
            showLoading(true)
            try {
                val response = withContext(Dispatchers.IO) {
                    ReTrofit.instance.getBusSchedule().execute().body()
                }
                response?.data?.let { schedules ->
                    if (schedules.isNotEmpty()) {
                        busData.clear()
                        busData.addAll(schedules)
                    } else {
                        showToast("No bus schedules available")
                    }
                } ?: showToast("Failed to load bus data")
            } catch (e: Exception) {
                handleError("Error loading bus data", e)
            } finally {
                showLoading(false)
            }
        }
    }

    private fun fetchBusData(busNumber: String) {
        fetchJob = CoroutineScope(Dispatchers.Main).launch {
            showLoading(true)
            try {
                val response = withContext(Dispatchers.IO) {
                    ReTrofit.instance.getBusTracking(busNumber).execute().body()
                }
                when {
                    response == null -> showToast("No data found for bus $busNumber")
                    response.data.isEmpty() -> showToast("Empty response for bus $busNumber")
                    else -> updateUI(response.data.first())
                }
            } catch (e: Exception) {
                handleError("Error fetching bus data", e)
            } finally {
                showLoading(false)
            }
        }
    }

    private fun updateUI(busData: UpdateTracking) {
        try {
            with(busData) {
                val routeStations = listOf(previouslocation, currentlocation, nextlocation)

                binding.apply {
                    currentLocationText.text = "Current Location: $currentlocation"
                    nextStationText.text = "Next Station: $nextlocation"
                    currentLocationText2.text = "Previous Station: $previouslocation"
                }

                updateSeekBar(currentlocation, nextlocation, routeStations)
            }
        } catch (e: Exception) {
            handleError("Error updating UI", e)
        }
    }

    private fun updateSeekBar(
        currentLocation: String,
        nextStation: String,
        stations: List<String>
    ) {
        if (stations.isEmpty()) {
            resetSeekBar()
            return
        }

        val currentIndex = stations.indexOf(currentLocation)
        if (currentIndex >= 0) {
            // Calculate progress between current and next station
            val progress = ((currentIndex + 0.5) / stations.size * 100).toInt()
                .coerceIn(0, 100)

            binding.routeProgressBar.progress = progress

            // Update station labels with fallback to empty string
            binding.apply {
                station1.text = stations.getOrNull(currentIndex).orEmpty()
                station2.text = stations.getOrNull(currentIndex + 1).orEmpty()
                station3.text = stations.getOrNull(currentIndex + 2).orEmpty()
            }
        } else {
            resetSeekBar()
        }
    }

    private fun resetSeekBar() {
        binding.apply {
            routeProgressBar.progress = 0
            station1.text = ""
            station2.text = ""
            station3.text = ""
        }
    }

    private fun showLoading(show: Boolean) {
        binding.loadingIndicator.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun handleError(context: String, exception: Exception) {
        exception.printStackTrace()
        showToast("$context: ${exception.message ?: "Unknown error"}")
    }
}
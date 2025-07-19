package com.example.bustract.Driver

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bustract.Responses.ReTrofit
import com.example.bustract.databinding.ActivityUpdateRouteBinding
import com.example.bustract.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UpdateRouteActivity : AppCompatActivity() {
    private val binding by lazy { ActivityUpdateRouteBinding.inflate(layoutInflater) }
    private val deportlist = ArrayList<String>()
    private val shared by lazy { getSharedPreferences("user", MODE_PRIVATE) }


    private var vehicleNumber: String? = null
    private var routeId: String? = null
    private val stationList = ArrayList<String>() // List to hold station names from server

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        vehicleNumber = shared.getString("rrr", "")
        routeId = shared.getString("vvv", "")
        showToast("$vehicleNumber")
        binding.vehicleNumberText.text = vehicleNumber
        CoroutineScope(IO).launch {
            try {
                ReTrofit.instance.getDeportId().execute().body()?.let {
                    val typcheck = it.data!!.filter { it.type == "Deport" }
                    val list = typcheck.map { it.name ?: "" }
                    withContext(Dispatchers.Main) {
                        deportlist.clear()
                        deportlist.addAll(list)
                        Log.d("DEPOT_LIST", "Depot list: $deportlist")
                        setupSpinners(deportlist)
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        binding.vehicleNumberText.text = "Vehicle Number: $vehicleNumber"
        showToast("$routeId")

        setupUpdateButton()

    }


    private fun setupSpinners(list: ArrayList<String>) {
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.previousStationSpinner.adapter = adapter1
        binding.currentStationSpinner.adapter = adapter1
        binding.nextStationSpinner.adapter = adapter1
    }

    private fun setupUpdateButton() {
        binding.updateButton.setOnClickListener {
            val previous = binding.previousStationSpinner.selectedItem?.toString() ?: ""
            val current = binding.currentStationSpinner.selectedItem?.toString() ?: ""
            val next = binding.nextStationSpinner.selectedItem?.toString() ?: ""

            if (current.isEmpty()) {
                Toast.makeText(this, "Please select current station", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            updateDriverTracking(previous, current, next)
        }
    }


    private fun updateDriverTracking(previous: String, current: String, next: String) {
        CoroutineScope(IO).async {
            binding.loadingIndicator.visibility = android.view.View.VISIBLE
            ReTrofit.instance.updateTracking(
                vehiclenumber = vehicleNumber!!,
                routeid = routeId!!,
                currentlocation = current,
                previouslocation = previous,
                nextlocation = next,
                "${System.currentTimeMillis()}"
            ).execute().body()?.let {
                if (it.error == false) {
                    withContext(Main) {
                        binding.loadingIndicator.visibility = android.view.View.INVISIBLE
                        showToast("${it.message}")
                    }

                } else {
                    withContext(Main) {
                        binding.loadingIndicator.visibility = android.view.View.INVISIBLE
                        showToast("${it.message}")
                    }

                }

            }

        }


    }
}








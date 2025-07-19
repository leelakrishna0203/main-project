package com.example.bustract.BusDepot

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.bustract.Responses.CommonResponse
import com.example.bustract.Responses.ReTrofit
import com.example.bustract.databinding.ActivityAddScheduleBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddScheduleActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddScheduleBinding.inflate(layoutInflater) }
    private val shared by lazy { getSharedPreferences("user", MODE_PRIVATE) }
    var depotId = ""
    var routeId = ""

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val key1 = intent.getStringExtra("key1")
        val key2 = intent.getStringExtra("key2")
        val key3 = intent.getStringExtra("key3")
        val key4 = intent.getStringExtra("key4")


        binding.etVehicleNumber.text = "Vehicle Number : ${key1}"
        binding.etDepotId.text = "From : ${key2}"
        binding.etRouteId.text = "To : ${key3}"

        binding.btnSubmit.setOnClickListener {
            val platformNumber = binding.etPlatformNumber.text.toString().trim()
            val arrivalTime = binding.etArrivalTime.text.toString().trim()
            val departureTime = binding.etDepartureTime.text.toString().trim()

            if (platformNumber.isEmpty() || arrivalTime.isEmpty() || departureTime.isEmpty()) {
                showToast("Please fill all fields")
            } else {
                uploadSchedule(
                    "${key1}",
                    "${shared.getString("id", "")}",
                    "${key4}",
                    platformNumber,
                    arrivalTime,
                    departureTime
                )
            }
        }
    }

    private fun uploadSchedule(
        vehicleNumber: String,
        depotId: String,
        routeId: String,
        platformNumber: String,
        arrivalTime: String,
        departureTime: String,
    ) {
        ReTrofit.instance.addSchedule(
            vehicleNumber,
            depotId,
            routeId,
            platformNumber,
            arrivalTime,
            departureTime
        ).enqueue(object : Callback<CommonResponse> {
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>,
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        showToast(it.message)
                        if (!it.error) {
                            finish()
                        }
                    } ?: showToast("Server returned empty response")
                } else {
                    showToast("Server error: ${response.code()} - ${response.message()}")
                    Log.e("API_ERROR", "Response: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                showToast("Failed: ${t.message}")
                Log.e("API_FAILURE", t.stackTraceToString())
            }
        })
    }

    private fun showToast(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }
}
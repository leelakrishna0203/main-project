package com.example.bustract.BusDepot

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bustract.Adapters.ScheduledBusAdapter
import com.example.bustract.Responses.BusScheduleResponse
import com.example.bustract.Responses.ReTrofit
import com.example.bustract.databinding.ActivityScheduledBusesBinding
import com.example.bustract.showToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScheduledBuses : AppCompatActivity() {
    private val bind by lazy { ActivityScheduledBusesBinding.inflate(layoutInflater) }
    private val shared by lazy { getSharedPreferences("user", MODE_PRIVATE) }
    private lateinit var adapter: ScheduledBusAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        val deportid = intent.getStringExtra("id")
        adapter = ScheduledBusAdapter(emptyList())
        bind.rvSchedules.adapter = adapter
        bind.rvSchedules.layoutManager =
            LinearLayoutManager(this@ScheduledBuses)

        ReTrofit.instance.getBusSchedule().enqueue(object : Callback<BusScheduleResponse> {
            override fun onResponse(
                call: Call<BusScheduleResponse?>,
                response: Response<BusScheduleResponse?>,
            ) {
                val data = response.body()!!
                val list = data.data.filter { it.depotid == "$deportid" }
                Log.d("dkfjkdsjf", "list: $list")
                adapter.updateList(ArrayList(list))
            }

            override fun onFailure(
                call: Call<BusScheduleResponse?>,
                t: Throwable,
            ) {
                showToast("Server error : ${t.message}")
            }

        })


    }


}

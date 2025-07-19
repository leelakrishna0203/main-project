package com.example.bustract.BusDepot

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bustract.Adapters.BusScheduleAdapter
import com.example.bustract.LoginActivity
import com.example.bustract.Responses.GetHistoryUser
import com.example.bustract.Responses.ReTrofit
import com.example.bustract.User.ModuleActivity
import com.example.bustract.databinding.ActivityBusstandBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BusStandActivity : AppCompatActivity() {
    private val bind by lazy { ActivityBusstandBinding.inflate(layoutInflater) }
    private val shared by lazy { getSharedPreferences("user", MODE_PRIVATE) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)


        bind.checkschedules.setOnClickListener {
            startActivity(Intent(this, ScheduledBuses::class.java).apply {
                putExtra("id", shared.getString("id", ""))
            })
        }

        bind.buslogout.setOnClickListener {
            getSharedPreferences("user", MODE_PRIVATE).edit().clear().apply()
            finishAffinity()
            startActivity(Intent(this@BusStandActivity, ModuleActivity::class.java))
        }

        loadBusSchedules()
    }

    private fun loadBusSchedules() {
        CoroutineScope(IO).launch {
            ReTrofit.instance.getBusHistory(condition = "getBusHistory")
                .enqueue(object : Callback<GetHistoryUser> {
                    override fun onResponse(
                        call: Call<GetHistoryUser>,
                        response: Response<GetHistoryUser>,
                    ) {
                        response.body()?.let {
                            Log.d("fksdjfkd", "onResponse: ${it.data}")
                            val list = it.data.get(0)
                            bind.rvSchedulesList.adapter = BusScheduleAdapter(it.data) {
                                startActivity(
                                    Intent(
                                        this@BusStandActivity,
                                        AddScheduleActivity::class.java
                                    ).apply {
                                        putExtra("key1", it.vehiclenumber)
                                        putExtra("key2", it.fromplace)
                                        putExtra("key3", it.toplace)
                                        putExtra("key4", it.routeid)
                                    })
                            }
                            bind.rvSchedulesList.layoutManager =
                                LinearLayoutManager(this@BusStandActivity)
                        }

                    }

                    override fun onFailure(call: Call<GetHistoryUser>, t: Throwable) {

                    }
                })
        }
    }
}
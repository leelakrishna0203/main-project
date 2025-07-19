package com.example.bustract.Driver.Core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bustract.Adapters.FinalAdapter
import com.example.bustract.Comfun
import com.example.bustract.R
import com.example.bustract.Responses.LastResponse
import com.example.bustract.Responses.ReTrofit
import com.example.bustract.showToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewMoreDetails : AppCompatActivity() {


val cf by lazy {
    Comfun(this)
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_days)
    intent.getStringExtra("date")?.let {
        cf.p.show()
        val id=getSharedPreferences("user", MODE_PRIVATE).getString("id","")
        ReTrofit.instance.getDayHistory(condition = "getdaysHistory", id = "$id", date = it).enqueue(
            object :Callback<LastResponse>{
                override fun onResponse(
                    call: Call<LastResponse>,
                    response: Response<LastResponse>
                ) {
                    cf.p.dismiss()
                    response.body()?.let {
                        findViewById<RecyclerView>(R.id.cycle6).apply {
                     layoutManager=LinearLayoutManager(this@ViewMoreDetails)
                            adapter=FinalAdapter(this@ViewMoreDetails,it.data)
                        }
                    }
                }

                override fun onFailure(call: Call<LastResponse>, t: Throwable) {
                showToast(t.message)

                    cf.p.dismiss()
                    finish()
                }
            })

    }
    }
}

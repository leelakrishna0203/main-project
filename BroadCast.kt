package com.example.bustract.Driver.Core

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.bustract.Responses.CommonResponse
import com.example.bustract.Responses.ReTrofit
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class BroadCast:BroadcastReceiver() {
    lateinit var fused :FusedLocationProviderClient
    val decimal=DecimalFormat("##.#######")
    override fun onReceive(p0: Context?, p1: Intent?) {
        p0?.let {
            Log.i("ajshdjkfhadsf","I am in 😊")
            fused=LocationServices.getFusedLocationProviderClient(it)
            sendlocation(it) }
    }

    @SuppressLint("MissingPermission")
    private fun sendlocation(my: Context) {
        fused.lastLocation.addOnSuccessListener {
          if(it!=null){
              val the=my.getSharedPreferences("user",0).getString("id","")
              ReTrofit.instance.updatelocation(latlon = "${decimal.format(it.latitude)},${decimal.format(it.longitude)}"
                  , driverid = "$the").enqueue(object :Callback<CommonResponse>{
                  override fun onResponse(
                      call: Call<CommonResponse>,
                      response: Response<CommonResponse>
                  ) {
                      Log.i("asdfadfhsdfhsd","${response.body()}")
                  }

                  override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                      Log.i("asdfadfhsdfhsd","${t.message}")
                  }
              })
          }
            }
    }
}
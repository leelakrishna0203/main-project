package com.example.bustract.User.Core

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bustract.Responses.Model.Route
import com.example.bustract.Responses.ReTrofit
import com.example.bustract.Responses.RouteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeatViewModel : ViewModel() {
    private val mutable = MutableLiveData(ArrayList<Route>())
    fun startData(search: String) {
        ReTrofit.instance.getRouteTrack(condition = "getTrackHistory", search = search)
            .enqueue(object : Callback<RouteResponse> {
                override fun onResponse(
                    call: Call<RouteResponse>,
                    response: Response<RouteResponse>,
                ) {
                    response.body()?.let {
                        mutable.value = it.data
                    }
                }

                override fun onFailure(call: Call<RouteResponse>, t: Throwable) {
                    Log.i("asdkfgasdgfds", "${t.message}")
                }
            })
    }

    fun viewdata() = mutable
}
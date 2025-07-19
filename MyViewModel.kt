package com.example.bustract.Admin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bustract.Responses.CustomResponse
import com.example.bustract.Responses.Model.Route
import com.example.bustract.Responses.Model.User
import com.example.bustract.Responses.ReTrofit
import com.example.bustract.Responses.RouteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyViewModel:ViewModel() {
private val mutable =MutableLiveData(ArrayList<Route>())
    private val data=MutableLiveData(ArrayList<User>())
    fun getdrviers(){
        ReTrofit.instance.getdrviers(condition = "getdrivers").enqueue(object :Callback<CustomResponse>{
            override fun onResponse(
                call: Call<CustomResponse>,
                response: Response<CustomResponse>
            ) {
                response.body()?.let {
                    data.value=it.data
                }
            }

            override fun onFailure(call: Call<CustomResponse>, t: Throwable) {}
        })

    }

    fun getdeports(){
        ReTrofit.instance.getdrviers(condition = "getdrivers2").enqueue(object :Callback<CustomResponse>{
            override fun onResponse(
                call: Call<CustomResponse>,
                response: Response<CustomResponse>
            ) {
                response.body()?.let {
                    data.value=it.data
                }
            }

            override fun onFailure(call: Call<CustomResponse>, t: Throwable) {}
        })

    }


    fun getdata(){
        ReTrofit.instance.getdataofusers("getlist").enqueue(object :Callback<RouteResponse>{
            override fun onResponse(call: Call<RouteResponse>, response: Response<RouteResponse>) {
                response.body()?.let {
                    mutable.value=it.data
                }
            }

            override fun onFailure(call: Call<RouteResponse>, t: Throwable) {
       mutable.value=null
            }
        })
    }


    fun contains(string :String){
        val newdata=ArrayList<User>()
        data.value?.forEach {
            if(it.name?.contains(string) == true){
                newdata.add(it)
            }else if(it.mobile?.contains(string)==true){
                newdata.add(it)
            }else{
                newdata.add(it)
            }
        }
        data.value=newdata
    }


    fun myobserver()=mutable
    fun observedrvier()=data







}
package com.example.bustract.Responses

import com.example.bustract.Responses.Model.BusSchedule
import com.example.bustract.Responses.Model.UpdateTracking
import com.google.gson.annotations.SerializedName

data class BusScheduleResponse(
    @SerializedName("error") var error: Boolean? = null,
    @SerializedName("message") var message: String? = null,
  var data: ArrayList<BusSchedule> = arrayListOf(),
    var data2: ArrayList<UpdateTracking> = arrayListOf(),
)

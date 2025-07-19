package com.example.bustract.Responses

import com.example.bustract.Responses.Model.BusSchedule
import com.example.bustract.Responses.Model.UpdateTracking
import com.google.gson.annotations.SerializedName

data class BusTrackResponse(
    @SerializedName("error") var error: Boolean? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var data: ArrayList<UpdateTracking> = arrayListOf(),
)

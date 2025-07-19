package com.example.bustract.Responses

import com.example.bustract.Responses.Model.Custome
import com.google.gson.annotations.SerializedName

data class LastResponse (

    @SerializedName("error"   ) var error   : Boolean?        = null,
    @SerializedName("message" ) var message : String?         = null,
    @SerializedName("data"    ) var data    : ArrayList<Custome> = arrayListOf()
)
package com.example.bustract.Responses

import com.example.bustract.Responses.Model.User
import com.google.gson.annotations.SerializedName

data class CustomResponse (
    @SerializedName("error")var error:Boolean?=null,
    @SerializedName("message")var message:String?=null,
    @SerializedName("data")var data:ArrayList<User>?= arrayListOf()
)
package com.example.bustract.Responses

import com.example.bustract.Responses.Model.Search
import com.google.gson.annotations.SerializedName

data class SearchResponse (
    @SerializedName("error")var error:Boolean?=null,
    @SerializedName("message")var message:String?=null,
    @SerializedName("data")var data:ArrayList<Search>?= arrayListOf()
)
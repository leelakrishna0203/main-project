package com.example.bustract.Responses.Model

import com.google.gson.annotations.SerializedName

data class Seats
    (
    @SerializedName("id")var id:String?=null,
    @SerializedName("routeid")var routeid:String?=null,
    @SerializedName("payment")var payment:String?=null,
    @SerializedName("seats")var seats:String?=null,
    @SerializedName("datepayed")var datepayed:String?=null,
    @SerializedName("userid")var userid:String?=null,
    @SerializedName("total")var total:String?=null,
            )

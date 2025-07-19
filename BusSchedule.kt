package com.example.bustract.Responses.Model

data class BusSchedule(
    val id: String,
    val vehiclenumber: String,
    val depotid: String,
    val routeid: String,
    val platformnumber: String,
    val arrivaltime: String,
    val departuretime: String,
    val fromplace: String,
    val toplace: String,
    val fromlatlon: String,
    val tolatlon: String,
    val driverid: String,
    val latlon: String
)
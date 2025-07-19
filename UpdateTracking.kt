package com.example.bustract.Responses.Model

data class UpdateTracking(
    var id: String,
    var vehiclenumber: String,
    var routeid: String,
    var currentlocation: String,
    var previouslocation: String,
    var nextlocation: String,
    var timestamp: String
)

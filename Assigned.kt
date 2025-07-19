package com.example.bustract.Responses.Model

import com.google.gson.annotations.SerializedName

data class Assigned (
    @SerializedName("fromplace"     ) var fromplace     : String? = null,
    @SerializedName("toplace"       ) var toplace       : String? = null,
    @SerializedName("fromlatlon"    ) var fromlatlon    : String? = null,
    @SerializedName("tolatlon"      ) var tolatlon      : String? = null,
    @SerializedName("starttime"     ) var starttime     : String? = null,
    @SerializedName("endtime"       ) var endtime       : String? = null,
    @SerializedName("perkm"         ) var perkm         : String? = null,
    @SerializedName("days"          ) var days          : String? = null,
    @SerializedName("vehiclenumber" ) var vehiclenumber : String? = null,
    @SerializedName("routeid" ) var routeid : String? = null
)
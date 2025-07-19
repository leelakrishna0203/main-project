package com.example.bustract.Responses.Model

import com.google.gson.annotations.SerializedName

data
class Custome (
    @SerializedName("id"         ) var id         : String? = null,
    @SerializedName("payment"    ) var payment    : String? = null,
    @SerializedName("seats"      ) var seats      : String? = null,
    @SerializedName("datepayed"  ) var datepayed  : String? = null,
    @SerializedName("total"      ) var total      : String? = null,
    @SerializedName("name"       ) var name       : String? = null,
    @SerializedName("mobile"     ) var mobile     : String? = null,
    @SerializedName("mail"       ) var mail       : String? = null,
    @SerializedName("fromplace"  ) var fromplace  : String? = null,
    @SerializedName("toplace"    ) var toplace    : String? = null,
    @SerializedName("fromlatlon" ) var fromlatlon : String? = null,
    @SerializedName("tolatlon"   ) var tolatlon   : String? = null,
    @SerializedName("driverid"   ) var driverid   : String? = null,
    @SerializedName("latlon"     ) var latlon     : String? = null
)

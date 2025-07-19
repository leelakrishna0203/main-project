package com.example.bustract.Responses.Model

import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

data class History(
    @SerializedName("payment") var payment: String? = null,
    @SerializedName("seats") var seats: String? = null,
    @SerializedName("datepayed") var datepayed: String? = null,
    @SerializedName("total") var total: String? = null,
    @SerializedName("fromplace") var fromplace: String? = null,
    @SerializedName("toplace") var toplace: String? = null,
    @SerializedName("fromlatlon") var fromlatlon: String? = null,
    @SerializedName("profile") var profile: String? = null,
    @SerializedName("starttime") var starttime: String? = null,
    @SerializedName("endtime") var endtime: String? = null,
    @SerializedName("vehiclenumber") var vehiclenumber: String? = null,
    @SerializedName("perkm") var perkm: String? = null,
    @SerializedName("days") var days: String? = null,
    @SerializedName("latlon") var latlon: String? = null,
    @SerializedName("routeid") var routeid: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(payment)
        parcel.writeString(seats)
        parcel.writeString(datepayed)
        parcel.writeString(total)
        parcel.writeString(fromplace)
        parcel.writeString(toplace)
        parcel.writeString(fromlatlon)
        parcel.writeString(profile)
        parcel.writeString(starttime)
        parcel.writeString(endtime)
        parcel.writeString(vehiclenumber)
        parcel.writeString(perkm)
        parcel.writeString(days)
        parcel.writeString(latlon)
        parcel.writeString(routeid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<History> {
        override fun createFromParcel(parcel: Parcel): History {
            return History(parcel)
        }

        override fun newArray(size: Int): Array<History?> {
            return arrayOfNulls(size)
        }
    }
}
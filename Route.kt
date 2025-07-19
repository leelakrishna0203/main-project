package com.example.bustract.Responses.Model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Route (
    @SerializedName("id")var id:String?=null,
    @SerializedName("fromplace")var fromplace:String?=null,
    @SerializedName("toplace")var toplace:String?=null,
    @SerializedName("fromlatlon")var fromlatlon:String?=null,
    @SerializedName("tolatlon")var tolatlon:String?=null,
    @SerializedName("driverid")var driverid:String?=null,
    @SerializedName("latlon")var latlon:String?=null,
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(fromplace)
        parcel.writeString(toplace)
        parcel.writeString(fromlatlon)
        parcel.writeString(tolatlon)
        parcel.writeString(driverid)
        parcel.writeString(latlon)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Route> {
        override fun createFromParcel(parcel: Parcel): Route {
            return Route(parcel)
        }

        override fun newArray(size: Int): Array<Route?> {
            return arrayOfNulls(size)
        }
    }

}
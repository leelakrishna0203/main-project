package com.example.bustract.Responses.Model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Search (

    @SerializedName("id")var id:String?=null,
    @SerializedName("driverid")var driverid:String?=null,
    @SerializedName("routeid")var routeid:String?=null,
    @SerializedName("starttime")var starttime:String?=null,
    @SerializedName("endtime")var endtime:String?=null,
    @SerializedName("vehiclenumber")var vehiclenumber:String?=null,
    @SerializedName("perkm")var perkm:String?=null,
    @SerializedName("days")var days:String?=null,
    @SerializedName("fromplace")var fromplace:String?=null,
    @SerializedName("toplace")var toplace:String?=null,
    @SerializedName("fromlatlon")var fromlatlon:String?=null,
    @SerializedName("tolatlon")var tolatlon:String?=null,
    @SerializedName("state")var state:Boolean?=false,
    @SerializedName("distance")var distance:Int?=null

):Parcelable{
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
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(driverid)
        parcel.writeString(routeid)
        parcel.writeString(starttime)
        parcel.writeString(endtime)
        parcel.writeString(vehiclenumber)
        parcel.writeString(perkm)
        parcel.writeString(days)
        parcel.writeString(fromplace)
        parcel.writeString(toplace)
        parcel.writeString(fromlatlon)
        parcel.writeString(tolatlon)
        parcel.writeValue(state)
        parcel.writeValue(distance)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Search> {
        override fun createFromParcel(parcel: Parcel): Search {
            return Search(parcel)
        }

        override fun newArray(size: Int): Array<Search?> {
            return arrayOfNulls(size)
        }
    }


}
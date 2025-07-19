package com.example.bustract.Responses.Model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("mobile") var mobile: String? = null,
    @SerializedName("mail") var mail: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("profile") var profile: String? = null,
    @SerializedName("drivinglicense") var drivinglicense: String? = null,
    @SerializedName("location") var location: String? = null,
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
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(mobile)
        parcel.writeString(mail)
        parcel.writeString(password)
        parcel.writeString(type)
        parcel.writeString(profile)
        parcel.writeString(drivinglicense)
        parcel.writeString(location)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}
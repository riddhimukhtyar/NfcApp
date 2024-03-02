package com.example.nfcapp

import android.os.Parcel
import android.os.Parcelable

class ScreenItem(var title: String, var description: String, var screenImg: Int) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeInt(screenImg)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ScreenItem> {
        override fun createFromParcel(parcel: Parcel): ScreenItem {
            return ScreenItem(parcel)
        }

        override fun newArray(size: Int): Array<ScreenItem?> {
            return arrayOfNulls(size)
        }
    }
}

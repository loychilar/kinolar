package com.example.kinolar.adapters

import android.os.Parcel
import android.os.Parcelable

class FilmAdapter() : Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FilmAdapter> {
        override fun createFromParcel(parcel: Parcel): FilmAdapter {
            return FilmAdapter(parcel)
        }

        override fun newArray(size: Int): Array<FilmAdapter?> {
            return arrayOfNulls(size)
        }
    }
}
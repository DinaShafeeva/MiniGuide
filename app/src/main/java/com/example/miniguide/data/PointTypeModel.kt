package com.example.miniguide.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class PointTypeModel : Parcelable {
    START_POINT,
    END_POINT
}
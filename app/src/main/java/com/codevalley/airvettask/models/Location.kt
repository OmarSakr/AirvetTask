package com.codevalley.airvettask.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "country") val country: String,
) : Parcelable
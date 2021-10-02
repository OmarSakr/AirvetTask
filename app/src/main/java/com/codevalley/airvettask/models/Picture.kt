package com.codevalley.airvettask.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class Picture(
    @ColumnInfo(name = "large") val large: String,
) : Parcelable
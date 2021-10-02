package com.codevalley.airvettask.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class Name(
    @ColumnInfo(name = "first") val first: String,
):Parcelable
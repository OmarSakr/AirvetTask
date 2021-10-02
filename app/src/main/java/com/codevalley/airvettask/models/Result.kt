package com.codevalley.airvettask.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "userData")
data class Result(
    @PrimaryKey(autoGenerate = true) val uniqueId: Int,
    val cell: String,
    val email: String,
    val gender: String,
    @Embedded
    val location: Location,
    @Embedded
    val name: Name,
    val nat: String,
    val phone: String,
    @Embedded
    val picture: Picture,
):Parcelable
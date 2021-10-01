package com.codevalley.airvettask.models

data class Result(
    val cell: String,
    val email: String,
    val gender: String,
    val location: Location,
    val name: Name,
    val nat: String,
    val phone: String,
    val picture: Picture,
)
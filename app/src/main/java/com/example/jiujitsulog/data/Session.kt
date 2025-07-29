package com.example.jiujitsulog.data

data class Session(
    val date: String,
    val classType: String,
    val rounds: Int,
    val submissions: Int,
    val position: String,
    val notes: String
)

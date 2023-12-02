package com.example.inteamfit.model

data class Exercise(
    val name: String,
    var sets: Int,
    var reps: Int,
    var checked: Boolean = false
)

package com.example.inteamfit.model

data class Workout(
    val id: String,
    val title: String,
    val date: String,
    val notes: String,
    val exercises: List<Exercise>
)

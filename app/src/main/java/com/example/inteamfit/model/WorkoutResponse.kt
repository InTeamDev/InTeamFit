package com.example.inteamfit.model

data class WorkoutResponse(
    val id: String,
    val title: String,
    val date: String,
    val notes: String,
    val exercises: List<ExerciseDetail>
)
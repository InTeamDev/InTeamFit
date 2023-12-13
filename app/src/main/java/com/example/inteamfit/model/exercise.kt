package com.example.inteamfit.model

data class Exercise(
    val name: String,
    val description: String,
    val muscleGroup: String
)


data class ExerciseDetail(
    val exercise: Exercise,
    val sets: Int,
    val reps: Int
)

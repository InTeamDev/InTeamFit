package com.example.inteamfit.api

import com.example.inteamfit.model.Workout
import retrofit2.http.GET


interface WorkoutService {
    @GET("workouts")
    suspend fun getWorkout(): Workout
}

package com.example.inteamfit.api

import com.example.inteamfit.model.WorkoutResponse
import com.example.inteamfit.model.WorkoutSlot
import retrofit2.http.GET
import retrofit2.http.Query

interface WorkoutApiService {
    @GET("api/workouts/slots")
    suspend fun getWorkoutSlots(): List<WorkoutSlot>

    @GET("api/workouts")
    suspend fun getWorkoutDetails(@Query("workout_date") workoutDate: String): WorkoutResponse
}

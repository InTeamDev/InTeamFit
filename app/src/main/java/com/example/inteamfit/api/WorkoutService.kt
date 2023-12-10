package com.example.inteamfit.api

import com.example.inteamfit.model.Workout
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.ContentType.Application.Json
import java.time.LocalDate

class WorkoutService (private val client: HttpClient) {
    suspend fun getWorkouts(date: LocalDate): List<Workout> {
        val response = client.get("http://localhost:8000/api/workouts/${date}")
        return Json.decodeFromString<Workout>(response)
    }
}
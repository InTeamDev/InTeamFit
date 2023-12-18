package com.example.inteamfit.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://fit.zetoqqq.me/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val workoutApi: WorkoutService by lazy {
        retrofit.create(WorkoutService::class.java)
    }

    val equipmentApi: EquipmentService by lazy {
        retrofit.create(EquipmentService::class.java)
    }
}

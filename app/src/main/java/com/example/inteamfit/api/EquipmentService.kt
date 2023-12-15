package com.example.inteamfit.api

import com.example.inteamfit.model.Equipment
import com.example.inteamfit.model.Workout
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

interface EquipmentService {
    @Multipart
    @POST("equipment/predict")
    fun uploadFile(@Part file: MultipartBody.Part): Call<List<Equipment>>
}
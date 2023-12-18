package com.example.inteamfit.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.inteamfit.api.EquipmentService
import com.example.inteamfit.api.WorkoutService
import com.example.inteamfit.model.Equipment
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EquipmentViewModel(private val apiService: EquipmentService) : ViewModel() {
    var equipments = MutableLiveData<List<Equipment>>(listOf())

    fun uploadFile(body:  MultipartBody.Part, navController: NavController) {
        viewModelScope.launch {
            try {
                apiService.uploadFile(body).enqueue(object :
                    Callback<List<Equipment>> {
                    override fun onResponse(call: Call<List<Equipment>>, response: Response<List<Equipment>>) {
                        if (response.isSuccessful) {
                            equipments.value = response.body()
                            navController.navigate("equipment")
                        } else {
                            Log.d("EQUIPMENT","Error: ${response.code()}")
                            navController.navigate("menu")
                        }
                    }

                    override fun onFailure(call: Call<List<Equipment>>, t: Throwable) {
                        Log.d("EQUIPMENT","Failure: ${t.message}")
                    }
                })
            } catch (e: Exception) {
                Log.d("EQUIPMENT", "uploadFile: ${e.message}")
            }
        }
    }
}
package com.example.inteamfit.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inteamfit.api.EquipmentService
import com.example.inteamfit.model.Equipment
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.navigation.NavController
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EquipmentViewModel : ViewModel() {
    var equipments = MutableLiveData<List<Equipment>>(listOf())

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(EquipmentService::class.java)

    fun uploadFile(body:  MultipartBody.Part, navController: NavController) {
        viewModelScope.launch {
            try {
                api.uploadFile(body).enqueue(object :
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
package com.example.inteamfit.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inteamfit.api.WorkoutService
import com.example.inteamfit.model.ExerciseDetail
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JournalViewModel : ViewModel() {
    var exercises = MutableLiveData<List<ExerciseDetail>>(listOf())
    var notes by mutableStateOf("")

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(WorkoutService::class.java)

    fun loadExercises() {
        viewModelScope.launch {
            try {
                Log.d("WORKOUT", "loadExercises start")
                val workout = api.getWorkout()
                Log.d("WORKOUT", "loadExercises: $workout")
                exercises.postValue(workout.exercises)
                notes = workout.notes
            } catch (e: Exception) {
                Log.d("WORKOUT", "loadExercises: ${e.message}")
            }
        }
    }
}

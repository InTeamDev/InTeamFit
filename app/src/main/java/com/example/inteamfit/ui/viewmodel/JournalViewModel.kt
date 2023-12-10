package com.example.inteamfit.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inteamfit.api.WorkoutService
import com.example.inteamfit.model.Exercise
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch
import java.time.LocalDate

class JournalViewModel(private val apiService: WorkoutService) : ViewModel() {
    val exercises = MutableLiveData<List<Exercise>>(listOf())
    var notes by mutableStateOf("")
    private var errorMessage = MutableLiveData<String>()

    fun loadExercises(date: LocalDate) {
        viewModelScope.launch {
            try {
                val response = apiService.getWorkouts(date)
                if (response.status == HttpStatusCode.OK) {
                    val workouts = response.body<List<Exercise>>()
                    exercises.postValue(workouts)
                } else {
                    errorMessage.postValue("Ошибка: Не удалось загрузить упражнения.")
                }
            } catch (e: Exception) {
                errorMessage.postValue("Исключение: ${e.message}")
            }
        }
    }
}

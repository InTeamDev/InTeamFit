package com.example.inteamfit.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inteamfit.api.WorkoutApiService
import com.example.inteamfit.model.WorkoutResponse
import com.example.inteamfit.model.WorkoutSlot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*

class WorkoutViewModel(private val apiService: WorkoutApiService) : ViewModel() {
    private val _workoutSlots = MutableStateFlow<List<WorkoutSlot>>(emptyList())
    val workoutSlots: StateFlow<List<WorkoutSlot>> = _workoutSlots

    private val _selectedDate = MutableStateFlow<String?>(null)
    val selectedDate: StateFlow<String?> = _selectedDate

    private val _workoutDetails = MutableStateFlow<WorkoutResponse?>(null)
    val workoutDetails: StateFlow<WorkoutResponse?> = _workoutDetails

    var notes = MutableStateFlow("")

    init {
        fetchWorkoutSlots()
        selectDate(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
    }

    private fun fetchWorkoutSlots() {
        viewModelScope.launch {
            _workoutSlots.value = apiService.getWorkoutSlots()
        }
    }

    fun selectDate(date: String) {
        _selectedDate.value = date
        fetchWorkoutDetails(date)
    }

    private fun fetchWorkoutDetails(date: String) {
        viewModelScope.launch {
            try {
                _workoutDetails.value = apiService.getWorkoutDetails(date)
            } catch (e: HttpException) {
                if (e.code() == 404) {
                    _workoutDetails.value = null
                } else {
                    // Handle other HTTP errors if necessary
                    Log.d("WORKOUT", "fetchWorkoutDetails return error: ${e.message()} with code: ${e.code()}")
                }
            } catch (e: Exception) {
                // Handle other types of exceptions (network errors, etc.)
                Log.d("WORKOUT", "fetchWorkoutDetails return error: ${e.message}")
            }
        }
    }
}

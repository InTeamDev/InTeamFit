package com.example.inteamfit.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inteamfit.model.Exercise

class JournalViewModel : ViewModel() {
    val exercises = MutableLiveData<List<Exercise>>(listOf())

    fun loadExercises() {
        exercises.value = listOf(
            Exercise("Отжимания", 3, 10),
            Exercise("Жим ногами", 3, 10),
            Exercise("Подтягивания", 3, 10)
        )
    }
}
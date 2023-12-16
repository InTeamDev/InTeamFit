package com.example.inteamfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.inteamfit.api.EquipmentService
import com.example.inteamfit.api.RetrofitInstance
import com.example.inteamfit.api.WorkoutService
import com.example.inteamfit.ui.screens.CameraViewScreen
import com.example.inteamfit.ui.screens.EquipmentScreen
import com.example.inteamfit.ui.screens.MyMenuScreen
import com.example.inteamfit.ui.screens.WorkoutScreen
import com.example.inteamfit.ui.viewmodel.EquipmentViewModel
import com.example.inteamfit.ui.viewmodel.WorkoutViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val equipViewModel = viewModel<EquipmentViewModel>(factory = EquipmentViewModelFactory(RetrofitInstance.equipmentApi))
    val workoutViewModel = viewModel<WorkoutViewModel>(factory = WorkoutViewModelFactory(RetrofitInstance.workoutApi))
    MaterialTheme {
        NavHost(navController = navController, startDestination = "menu") {
            composable("menu") { MyMenuScreen(navController) }
            composable("workout") { WorkoutScreen(navController, workoutViewModel) }
            composable("equipment") { EquipmentScreen(navController, equipViewModel) }
            composable("cameraView") { CameraViewScreen(navController, equipViewModel)}
        }
    }
}

class WorkoutViewModelFactory(private val apiService: WorkoutService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WorkoutViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class EquipmentViewModelFactory(private val apiService: EquipmentService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EquipmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EquipmentViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
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
import com.example.inteamfit.api.RetrofitInstance
import com.example.inteamfit.api.WorkoutApiService
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
    val equipViewModel: EquipmentViewModel = viewModel()
    val workoutViewModel = viewModel<WorkoutViewModel>(factory = WorkoutViewModelFactory(RetrofitInstance.api))
    MaterialTheme {
        NavHost(navController = navController, startDestination = "menu") {
            composable("menu") { MyMenuScreen(navController) }
            composable("workout") { WorkoutScreen(navController, workoutViewModel) }
            composable("equipment") { EquipmentScreen(navController, equipViewModel) }
            composable("cameraView") { CameraViewScreen(navController, equipViewModel)}
        }
    }
}

class WorkoutViewModelFactory(private val apiService: WorkoutApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WorkoutViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
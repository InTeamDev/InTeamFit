package com.example.inteamfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.inteamfit.ui.screens.EquipmentScreen
import com.example.inteamfit.ui.screens.MyMenuScreen
import com.example.inteamfit.ui.screens.JournalScreen

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
    MaterialTheme {
        NavHost(navController = navController, startDestination = "menu") {
            composable("menu") { MyMenuScreen(navController) }
            composable("journal") { JournalScreen(navController) }
            composable("equipment") { EquipmentScreen(navController) }
        }
    }
}
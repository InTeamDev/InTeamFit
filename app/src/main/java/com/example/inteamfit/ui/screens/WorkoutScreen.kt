package com.example.inteamfit.ui.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.inteamfit.model.WorkoutResponse
import com.example.inteamfit.ui.viewmodel.WorkoutViewModel
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen(navController: NavController, viewModel: WorkoutViewModel) {
    val selectedDate by viewModel.selectedDate.collectAsState()
    val workoutDetails by viewModel.workoutDetails.collectAsState()
    val notes by viewModel.notes.collectAsState()
    val context = LocalContext.current
    var showDatePickerDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, "Назад")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("Журнал тренировок", style = MaterialTheme.typography.titleMedium)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { showDatePickerDialog = true }) {
            Text("Выберите дату")
        }

        if (showDatePickerDialog) {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    val selected = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
                        GregorianCalendar(year, month, dayOfMonth).time
                    )
                    viewModel.selectDate(selected)
                    showDatePickerDialog = false
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        when {
            selectedDate == null -> Text("Выберите дату для просмотра тренировок")
            workoutDetails == null -> Text("На текущий день тренировок нет")
            else -> {
                WorkoutDetailsTable(workoutDetails!!)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = notes,
            onValueChange = { viewModel.notes.value = it },
            label = { Text("Заметки") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun WorkoutDetailsTable(workoutDetails: WorkoutResponse) {
    LazyColumn {
        items(workoutDetails.exercises) { exerciseDetail ->
            Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Text(exerciseDetail.exercise.name, modifier = Modifier.weight(1f))
                Text("${exerciseDetail.sets} подхода", modifier = Modifier.weight(1f))
                Text("${exerciseDetail.reps} повтора", modifier = Modifier.weight(1f))
            }
        }
    }
}
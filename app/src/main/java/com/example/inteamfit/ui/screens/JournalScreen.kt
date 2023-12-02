package com.example.inteamfit.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.inteamfit.model.Exercise
import com.example.inteamfit.ui.viewmodel.JournalViewModel
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen(navController: NavController, viewModel: JournalViewModel = viewModel()) {
    viewModel.loadExercises()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Журнал тренировок") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.height(66.dp))

            Text("План на сегодня", fontSize = 24.sp, style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            ExerciseTable(exercises = viewModel.exercises.value ?: emptyList())

            Spacer(modifier = Modifier.height(16.dp))

            Text("Заметки", fontSize = 24.sp, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.notes,
                onValueChange = { viewModel.notes = it },
                label = { Text("Введите ваши заметки здесь") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun ExerciseTable(exercises: List<Exercise>) {
    Column {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            Text("Упражнение", fontSize = 16.sp, modifier = Modifier.weight(1f))
            Text("Подходы", fontSize = 16.sp, modifier = Modifier.width(80.dp), textAlign = TextAlign.Right)
            Text("Повторения", fontSize = 16.sp, modifier = Modifier.width(100.dp), textAlign = TextAlign.Right)
        }

        LazyColumn {
            items(exercises) { exercise ->
                ExerciseRow(exercise = exercise, onCheckedChange = { checked ->
                    exercise.checked = checked
                })
            }
        }
    }
}

@Composable
fun ExerciseRow(exercise: Exercise, onCheckedChange: (Boolean) -> Unit) {
    val checkedState = remember { mutableStateOf(exercise.checked) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { isChecked ->
                checkedState.value = isChecked
                onCheckedChange(isChecked)
            },
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(exercise.name, fontSize = 18.sp, modifier = Modifier.weight(1f))
        Text("${exercise.sets}", fontSize = 18.sp, modifier = Modifier.width(80.dp), textAlign = TextAlign.Center)
        Text("${exercise.reps}", fontSize = 18.sp, modifier = Modifier.width(100.dp), textAlign = TextAlign.Center)
    }
}

package com.example.inteamfit.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.inteamfit.model.Equipment
import com.example.inteamfit.ui.viewmodel.EquipmentViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipmentScreen(navController: NavController, viewModel: EquipmentViewModel = viewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Возможные тренажеры") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("menu") }) {
                        Icon(Icons.Filled.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) {
        EquipmentTable(equipments = viewModel.equipments.value ?: emptyList())
    }
}

@Composable
fun EquipmentTable(equipments: List<Equipment>) {
    val headers = listOf("Name", "Description", "Probability")

    Column {
        Row(Modifier.fillMaxWidth().padding(8.dp)) {
            headers.forEach { header ->
                Text(
                    text = header,
                    modifier = Modifier.weight(1f).padding(4.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        equipments.forEach { equipment ->
            Row(Modifier.fillMaxWidth().padding(8.dp)) {
                Text(
                    text = equipment.name,
                    modifier = Modifier.weight(1f).padding(4.dp)
                )
                Text(
                    text = equipment.description,
                    modifier = Modifier.weight(1f).padding(4.dp)
                )
                Text(
                    text = equipment.probability.toString(),
                    modifier = Modifier.weight(1f).padding(4.dp)
                )
            }
        }
    }
}

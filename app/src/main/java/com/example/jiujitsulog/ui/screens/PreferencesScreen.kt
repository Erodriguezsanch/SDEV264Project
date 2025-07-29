package com.example.jiujitsulog.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun PreferencesScreen() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE) }

    var classType by remember { mutableStateOf("Gi") }
    var position by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        classType = prefs.getString("default_class_type", "Gi") ?: "Gi"
        position = prefs.getString("favorite_position", "") ?: ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Preferences", style = MaterialTheme.typography.headlineSmall)

        Text("Default Class Type")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            RadioButton(selected = classType == "Gi", onClick = { classType = "Gi" })
            Text("Gi")
            RadioButton(selected = classType == "No-Gi", onClick = { classType = "No-Gi" })
            Text("No-Gi")
        }

        OutlinedTextField(
            value = position,
            onValueChange = { position = it },
            label = { Text("Favorite Position") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            prefs.edit()
                .putString("default_class_type", classType)
                .putString("favorite_position", position)
                .apply()
            Toast.makeText(context, "Preferences saved!", Toast.LENGTH_SHORT).show()
        }) {
            Text("Save Preferences")
        }
    }
}

package com.example.jiujitsulog.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/**
 * PreferencesScreen allows users to configure their app settings.
 * Users can select a default training class type, set a favorite position,
 * and toggle between light and dark theme modes. Preferences are stored
 * using SharedPreferences and automatically loaded on screen entry.
 */
@Composable
fun PreferencesScreen() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE) }

    var classType by remember { mutableStateOf("Gi") }
    var position by remember { mutableStateOf("") }
    var darkMode by remember { mutableStateOf(false) }

    // Load saved preferences when the screen is first composed
    LaunchedEffect(Unit) {
        classType = prefs.getString("default_class_type", "Gi") ?: "Gi"
        position = prefs.getString("favorite_position", "") ?: ""
        darkMode = prefs.getBoolean("dark_mode", false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Preferences", style = MaterialTheme.typography.headlineSmall)

        // Gi / No-Gi selector
        Text("Default Class Type")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            RadioButton(selected = classType == "Gi", onClick = { classType = "Gi" })
            Text("Gi")
            RadioButton(selected = classType == "No-Gi", onClick = { classType = "No-Gi" })
            Text("No-Gi")
        }

        // Favorite training position input
        OutlinedTextField(
            value = position,
            onValueChange = { position = it },
            label = { Text("Favorite Position") },
            modifier = Modifier.fillMaxWidth()
        )

        // Dark mode toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dark Mode")
            Switch(
                checked = darkMode,
                onCheckedChange = { darkMode = it }
            )
        }

        // Save button
        Button(onClick = {
            prefs.edit()
                .putString("default_class_type", classType)
                .putString("favorite_position", position)
                .putBoolean("dark_mode", darkMode)
                .apply()
            Toast.makeText(context, "Preferences saved!", Toast.LENGTH_SHORT).show()
        }) {
            Text("Save Preferences")
        }
    }
}

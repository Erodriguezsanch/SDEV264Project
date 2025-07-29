package com.example.jiujitsulog.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.jiujitsulog.data.Session
import com.example.jiujitsulog.data.SessionRepository

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE) }

    var date by remember { mutableStateOf("") }
    var classType by remember { mutableStateOf("Gi") }
    var rounds by remember { mutableStateOf("") }
    var submissions by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    // Load saved preferences on launch
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
        Text(text = "Log Training Session", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            RadioButton(selected = classType == "Gi", onClick = { classType = "Gi" })
            Text("Gi")
            RadioButton(selected = classType == "No-Gi", onClick = { classType = "No-Gi" })
            Text("No-Gi")
        }

        OutlinedTextField(
            value = rounds,
            onValueChange = { rounds = it },
            label = { Text("Rounds Sparred") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = submissions,
            onValueChange = { submissions = it },
            label = { Text("Submissions") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = position,
            onValueChange = { position = it },
            label = { Text("Positional Focus") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Reflection / Notes") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val session = Session(
                    date = date,
                    classType = classType,
                    rounds = rounds.toIntOrNull() ?: 0,
                    submissions = submissions.toIntOrNull() ?: 0,
                    position = position,
                    notes = notes
                )
                SessionRepository.addSession(session)
                Toast.makeText(context, "Session Logged!", Toast.LENGTH_SHORT).show()

                date = ""
                rounds = ""
                submissions = ""
                position = prefs.getString("favorite_position", "") ?: ""
                notes = ""
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Log Session")
        }
    }
}
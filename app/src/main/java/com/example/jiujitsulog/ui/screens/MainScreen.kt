package com.example.jiujitsulog.ui.screens

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.jiujitsulog.data.Session
import com.example.jiujitsulog.data.SessionRepository
import java.text.SimpleDateFormat
import java.util.*

/**
 * MainScreen is the primary interface for logging Jiu-Jitsu training sessions.
 * Users can input date, class type, number of rounds, submissions, position focus, and notes.
 * Preferences such as default class type and favorite position are preloaded.
 * Sessions are stored locally.
 */
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE) }

    val calendar = remember { Calendar.getInstance() }
    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }

    // State variables for form input fields
    var date by remember { mutableStateOf(dateFormatter.format(calendar.time)) }
    var classType by remember { mutableStateOf("Gi") }
    var rounds by remember { mutableStateOf("") }
    var submissions by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    /**
     * Displays a DatePickerDialog and updates the selected date.
     */
    fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
        val parts = date.split("-")
        val year = parts.getOrNull(0)?.toIntOrNull() ?: calendar.get(Calendar.YEAR)
        val month = parts.getOrNull(1)?.toIntOrNull()?.minus(1) ?: calendar.get(Calendar.MONTH)
        val day = parts.getOrNull(2)?.toIntOrNull() ?: calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(context, { _: DatePicker, y: Int, m: Int, d: Int ->
            calendar.set(Calendar.YEAR, y)
            calendar.set(Calendar.MONTH, m)
            calendar.set(Calendar.DAY_OF_MONTH, d)
            onDateSelected(dateFormatter.format(calendar.time))
        }, year, month, day).show()
    }

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

        // Date input (non-editable, opens date picker)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker(context) { selected -> date = selected } }
        ) {
            OutlinedTextField(
                value = date,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Date") },
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = LocalContentColor.current,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
        // Class type selector
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            RadioButton(selected = classType == "Gi", onClick = { classType = "Gi" })
            Text("Gi")
            RadioButton(selected = classType == "No-Gi", onClick = { classType = "No-Gi" })
            Text("No-Gi")
        }

        // Round input
        OutlinedTextField(
            value = rounds,
            onValueChange = { rounds = it },
            label = { Text("Rounds Sparred") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Submission input
        OutlinedTextField(
            value = submissions,
            onValueChange = { submissions = it },
            label = { Text("Submissions") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Positional focus input
        OutlinedTextField(
            value = position,
            onValueChange = { position = it },
            label = { Text("Positional Focus") },
            modifier = Modifier.fillMaxWidth()
        )

        // Reflection / Notes input
        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Reflection / Notes") },
            modifier = Modifier.fillMaxWidth()
        )

        // Submits session to the SessionListScreen
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

                // Fields reset after logging a session
                date = dateFormatter.format(Calendar.getInstance().time)
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

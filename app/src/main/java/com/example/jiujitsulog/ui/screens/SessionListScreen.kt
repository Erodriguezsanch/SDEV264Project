package com.example.jiujitsulog.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.jiujitsulog.data.SessionRepository
import android.widget.Toast

/**
 * SessionListScreen displays a list of logged training sessions.
 * Sessions are loaded from the repository, sorted by date in descending order (newest first),
 * and presented in a scrollable list. Users can delete entries with confirmation.
 */
@Composable
fun SessionListScreen() {
    val context = LocalContext.current

    // Get sessions and sort by date (descending)
    val sessions = remember {
        mutableStateListOf(*SessionRepository.getSessions().sortedByDescending {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it.date)
        }.toTypedArray())
    }

    var deleteIndex by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Training Sessions", style = MaterialTheme.typography.headlineSmall)

        // Message for empty list
        if (sessions.isEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("No sessions logged yet.", style = MaterialTheme.typography.bodyLarge)
        } else {
            //Scrollable list
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(sessions) { index, session ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            // Session info display
                            Text("${session.date} - ${session.classType}", style = MaterialTheme.typography.titleMedium)
                            Text("Rounds: ${session.rounds}, Submissions: ${session.submissions}")
                            Text("Position: ${session.position}")
                            if (session.notes.isNotBlank()) Text("Notes: ${session.notes}")

                            Spacer(modifier = Modifier.height(4.dp))
                            // Delete button
                            Button(
                                onClick = { deleteIndex = index },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }
    }

    // Confirmation dialog
    if (deleteIndex != null) {
        AlertDialog(
            onDismissRequest = { deleteIndex = null },
            title = { Text("Delete Session") },
            text = { Text("Are you sure you want to delete this session?") },
            confirmButton = {
                TextButton(onClick = {
                    val idx = deleteIndex!!
                    SessionRepository.removeSession(idx)
                    sessions.removeAt(idx)
                    Toast.makeText(context, "Session deleted", Toast.LENGTH_SHORT).show()
                    deleteIndex = null
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { deleteIndex = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}
package com.example.jiujitsulog.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jiujitsulog.data.SessionRepository

@Composable
fun SessionListScreen() {
    val sessions = SessionRepository.getSessions().reversed()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Training Sessions", style = MaterialTheme.typography.headlineSmall)

        if (sessions.isEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("No sessions logged yet.", style = MaterialTheme.typography.bodyLarge)
        } else {
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
                            Text("${session.date} - ${session.classType}", style = MaterialTheme.typography.titleMedium)
                            Text("Rounds: ${session.rounds}, Submissions: ${session.submissions}")
                            Text("Position: ${session.position}")
                            if (session.notes.isNotBlank()) Text("Notes: ${session.notes}")

                            Spacer(modifier = Modifier.height(4.dp))
                            Button(
                                onClick = { SessionRepository.removeSession(index) },
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
}

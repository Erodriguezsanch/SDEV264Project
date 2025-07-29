package com.example.jiujitsulog.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HelpScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Help & App Info", style = MaterialTheme.typography.headlineSmall)

        Text("\u2022 Purpose: Jiu-Jitsu Log is designed to help practitioners track their training sessions, record key details, and reflect on progress over time.")

        Text("\u2022 Main Screen: Input training details like date, rounds, submissions, and notes. Tap \"Log Session\" to save it.")

        Text("\u2022 Sessions Screen: View previously logged training sessions. You’ll be able to edit or delete entries later.")

        Text("\u2022 Preferences: Set your default class type (Gi or No-Gi) and your favorite training position. Tap \"Save Preferences\" to persist them.")

        Text("\u2022 Preferences are automatically loaded when logging a new session, saving you time and effort.")

        Text("\u2022 Theme support (light/dark) coming soon!")
    }
}

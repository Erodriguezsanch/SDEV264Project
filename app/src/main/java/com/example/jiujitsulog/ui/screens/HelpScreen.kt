package com.example.jiujitsulog.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * HelpScreen provides users with basic instructions and an overview of how to use the app.
 * This screen is static and non-interactive. It explains the purpose of each main feature,
 * including logging sessions, viewing history, and saving preferences.
 */
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

        Text("\u2022 Purpose: Jiu-Jitsu Log is designed to help practitioners track their " +
                "training sessions, record key details, and reflect on progress over time.")

        Text("\u2022 Main Screen: Input training details like date, rounds, submissions, and " +
                "notes. Tap \"Log Session\" to save it.")

        Text("\u2022 Sessions Screen: View previously logged training sessions in descending " +
                "order (Newest First). You’ll be able to delete entries with confirmation.")

        Text("\u2022 Preferences: Set your default class type (Gi or No-Gi), your favorite" +
                " training position, and theme mode. Tap \"Save Preferences\" to persist them. In " +
                "order to apply the theme settings, close out the app and rerun it after pressing button.")

        Text("\u2022 Preferences are automatically loaded when logging a new session, saving " +
                "you time and effort.")
    }
}

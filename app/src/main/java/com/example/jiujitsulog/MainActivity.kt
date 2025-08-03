package com.example.jiujitsulog

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jiujitsulog.data.SessionRepository
import com.example.jiujitsulog.ui.screens.MainScreen
import com.example.jiujitsulog.ui.screens.SessionListScreen
import com.example.jiujitsulog.ui.screens.PreferencesScreen
import com.example.jiujitsulog.ui.screens.HelpScreen
import com.example.jiujitsulog.ui.theme.JiuJitsuLogTheme

/**
 * MainActivity serves as the entry point for the app.
 * It loads user preferences (like dark mode), initializes the session repository,
 * and sets up the Composable UI with bottom navigation and theming.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Theme preferences from SharedPreferences
        val prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val useDarkMode = prefs.getBoolean("dark_mode", false)

        // Initializes database wrapper
        SessionRepository.init(applicationContext)

        setContent {
            JiuJitsuLogTheme(useDarkTheme = useDarkMode) {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) }
                ) { innerPadding ->
                    NavigationGraph(navController, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

/**
 * BottomNavigationBar displays navigation icons to switch between app screens.
 * navController Handles the navigation action that changes the screen.
 */
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("main", Icons.Default.Home, "Log"),
        BottomNavItem("sessions", Icons.AutoMirrored.Filled.List, "Sessions"),
        BottomNavItem("preferences", Icons.Default.Settings, "Prefs"),
        BottomNavItem("help", Icons.Default.Build, "Help")
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route)
                    }
                }
            )
        }
    }
}

/**
 * Data class representing a navigation item in the bottom bar.
 * route:  The unique string used for navigation routing.
 * icon: The icon shown in the bottom nav bar.
 * label: The label shown under the icon.
 */
data class BottomNavItem(val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val label: String)

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(navController, startDestination = "main", modifier = modifier) {
        composable("main") { MainScreen() }
        composable("sessions") { SessionListScreen() }
        composable("preferences") { PreferencesScreen() }
        composable("help") { HelpScreen() }
    }
}


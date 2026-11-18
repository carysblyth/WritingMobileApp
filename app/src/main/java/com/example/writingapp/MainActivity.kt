
package com.example.writingapp

import androidx.compose.ui.platform.LocalContext
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.writingapp.ui.theme.WritingAppTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            WritingAppTheme {

                // Get the app's context
                val context = LocalContext.current

                // Load previously saved writing entries
                val entries = remember {
                    mutableStateListOf<WritingEntry>().apply {
                        addAll(loadEntries(context))
                    }
                }

                val navController = rememberNavController()

                val currentBackStackEntry by
                navController.currentBackStackEntryAsState()

                val currentRoute =
                    currentBackStackEntry?.destination?.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),

                    bottomBar = {
                        NavigationBar {

                            NavigationBarItem(
                                selected = currentRoute == "home",
                                onClick = {
                                    navController.navigate("home") {
                                        launchSingleTop = true
                                    }
                                },
                                icon = { Text("⌂") },
                                label = { Text("Home") }
                            )

                            NavigationBarItem(
                                selected = currentRoute == "stats",
                                onClick = {
                                    navController.navigate("stats") {
                                        launchSingleTop = true
                                    }
                                },
                                icon = { Text("📊") },
                                label = { Text("Stats") }
                            )

                            NavigationBarItem(
                                selected = currentRoute == "history",
                                onClick = {
                                    navController.navigate("history") {
                                        launchSingleTop = true
                                    }
                                },
                                icon = { Text("📅") },
                                label = { Text("History") }
                            )
                        }
                    }

                ) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        composable("home") {
                            HomeScreen(
                                entries = entries,
                                onAddWords = { words ->

                                    val date = SimpleDateFormat(
                                        "yyyy-MM-dd",
                                        Locale.getDefault()
                                    ).format(Date())

                                    entries.add(
                                        WritingEntry(date, words)
                                    )

                                    // Save the updated entries
                                    saveEntries(context, entries)
                                }
                            )
                        }

                        composable("stats") {
                            StatsScreen(entries = entries)
                        }

                        composable("history") {
                            HistoryScreen(entries = entries)
                        }
                        }
                    }
                }
            }
        }
    }



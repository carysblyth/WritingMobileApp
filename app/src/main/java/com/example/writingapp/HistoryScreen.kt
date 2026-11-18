
package com.example.writingapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HistoryScreen(entries: List<WritingEntry>) {

    // Group entries by date and calculate daily totals
    val dailyEntries = entries
        .groupBy { it.date }
        .map { (date, dayEntries) ->
            date to dayEntries.sumOf { it.words }
        }
        .sortedByDescending { it.first }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {

        Text(
            text = "Writing History",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (dailyEntries.isEmpty()) {

            Text(
                text = "No writing entries yet!",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Start writing and your daily progress will appear here."
            )

        } else {

            dailyEntries.forEach { (date, words) ->

                // Convert date into a readable format
                val inputFormat = SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
                )

                val outputFormat = SimpleDateFormat(
                    "EEEE, d MMMM yyyy",
                    Locale.getDefault()
                )

                val readableDate = inputFormat.parse(date)
                    ?.let { outputFormat.format(it) }
                    ?: date

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = readableDate,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = "Words written"
                            )
                        }

                        Text(
                            text = "$words",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            }
        }
    }
}


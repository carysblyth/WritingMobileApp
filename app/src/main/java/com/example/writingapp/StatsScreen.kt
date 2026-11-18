
package com.example.writingapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun StatsScreen(entries: List<WritingEntry>) {

    // Calculate total words
    val totalWords = entries.sumOf { it.words }

    // Count the number of different days with writing entries
    val daysWritten = entries
        .map { it.date }
        .distinct()
        .size

    // Calculate daily average
    val dailyAverage = if (daysWritten > 0) {
        totalWords / daysWritten
    } else {
        0
    }

    // Calculate words written this month
    val currentMonth = SimpleDateFormat(
        "yyyy-MM",
        Locale.getDefault()
    ).format(Date())

    val monthlyWords = entries
        .filter { it.date.startsWith(currentMonth) }
        .sumOf { it.words }

    // Calculate current writing streak

    val dateFormat = SimpleDateFormat(
        "yyyy-MM-dd",
        Locale.getDefault()
    )

    val writingDates = entries
        .map { it.date }
        .toSet()

    val calendar = java.util.Calendar.getInstance()

    val today = dateFormat.format(calendar.time)

// Check yesterday as well
    calendar.add(java.util.Calendar.DAY_OF_YEAR, -1)

    val yesterday = dateFormat.format(calendar.time)

// Decide where the streak should start
    val startDate = when {
        today in writingDates -> today
        yesterday in writingDates -> yesterday
        else -> null
    }

    var currentStreak = 0

    if (startDate != null) {

        var checkDate = dateFormat.parse(startDate)!!

        while (dateFormat.format(checkDate) in writingDates) {

            currentStreak++

            calendar.time = checkDate
            calendar.add(java.util.Calendar.DAY_OF_YEAR, -1)

            checkDate = calendar.time
        }
    }

    // Screen layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Your Statistics",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Total Words Written")

                Text(
                    text = "$totalWords",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Daily Average")

                Text(
                    text = "$dailyAverage words",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text("🔥 Current Writing Streak")

                Text(
                    text = "$currentStreak days",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Words Written This Month")

                Text(
                    text = "$monthlyWords words",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    }
}

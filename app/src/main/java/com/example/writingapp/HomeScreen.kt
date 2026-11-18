
package com.example.writingapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    entries: List<WritingEntry>,
    onAddWords: (Int) -> Unit
) {

    // Store the motivational quote
    var quote by remember {
        mutableStateOf("Loading motivational quote...")
    }

    // Store the quote author
    var author by remember {
        mutableStateOf("")
    }

    // Fetch a quote when the screen opens
    LaunchedEffect(Unit) {
        fetchRandomQuote { newQuote, newAuthor ->
            quote = newQuote
            author = newAuthor
        }
    }

    var wordInput by remember {
        mutableStateOf("")
    }

    val today = SimpleDateFormat(
        "yyyy-MM-dd",
        Locale.getDefault()
    ).format(Date())

    val todayWordCount = entries
        .filter { it.date == today }
        .sumOf { it.words }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "Writing Tracker",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Good morning!",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text("Today's Progress")

                Text(
                    text = "$todayWordCount words",
                    style = MaterialTheme.typography.headlineLarge
                )

                Text("Daily goal: 1,000 words")
            }
        }

        OutlinedTextField(
            value = wordInput,
            onValueChange = { wordInput = it },
            label = { Text("Words written") },
            singleLine = true
        )

        Button(
            onClick = {
                val words = wordInput.toIntOrNull()

                if (words != null && words > 0) {
                    onAddWords(words)
                    wordInput = ""
                }
            }
        ) {
            Text("Add Words")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text("✨ Daily Motivation")

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = quote
                )

                Text(
                    text = "- $author"
                )
            }
        }
    }
}

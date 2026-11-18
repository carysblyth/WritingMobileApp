
package com.example.writingapp

import android.os.Handler
import android.os.Looper
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

fun fetchRandomQuote(
    onResult: (String, String) -> Unit
) {

    thread {

        try {

            // Connect to the quotes API
            val url = URL("https://dummyjson.com/quotes/random")

            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            // Read the response
            val response = connection.inputStream
                .bufferedReader()
                .use { it.readText() }

            // Convert the response into JSON
            val jsonObject = JSONObject(response)

            val quote = jsonObject.getString("quote")
            val author = jsonObject.getString("author")

            connection.disconnect()

            // Return the quote to the main thread
            Handler(Looper.getMainLooper()).post {
                onResult(quote, author)
            }

        } catch (e: Exception) {

            // Return a fallback quote if something goes wrong
            Handler(Looper.getMainLooper()).post {
                onResult(
                    "The secret of getting ahead is getting started.",
                    "Mark Twain"
                )
            }
        }
    }
}

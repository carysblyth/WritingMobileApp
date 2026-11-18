
package com.example.writingapp

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

// Save writing entries to the device
fun saveEntries(
    context: Context,
    entries: List<WritingEntry>
) {

    val sharedPreferences = context.getSharedPreferences(
        "writing_tracker",
        Context.MODE_PRIVATE
    )

    val jsonArray = JSONArray()

    // Convert each writing entry into JSON
    for (entry in entries) {

        val jsonObject = JSONObject()

        jsonObject.put("date", entry.date)
        jsonObject.put("words", entry.words)

        jsonArray.put(jsonObject)
    }

    // Save the JSON data
    sharedPreferences.edit()
        .putString("entries", jsonArray.toString())
        .apply()
}


// Load writing entries from the device
fun loadEntries(context: Context): List<WritingEntry> {

    val sharedPreferences = context.getSharedPreferences(
        "writing_tracker",
        Context.MODE_PRIVATE
    )

    val savedEntries = sharedPreferences.getString(
        "entries",
        null
    )

    // Return an empty list if nothing has been saved
    if (savedEntries == null) {
        return emptyList()
    }

    val entries = mutableListOf<WritingEntry>()

    // Convert the saved JSON back into writing entries
    try {

        val jsonArray = JSONArray(savedEntries)

        for (i in 0 until jsonArray.length()) {

            val jsonObject = jsonArray.getJSONObject(i)

            val date = jsonObject.getString("date")
            val words = jsonObject.getInt("words")

            entries.add(
                WritingEntry(date, words)
            )
        }

    } catch (e: Exception) {
        return emptyList()
    }

    return entries
}


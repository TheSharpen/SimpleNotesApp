package com.example.simplenotesapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity //tale name defaults to class name, in this case table name = Note
data class Note(

    val title: String,
    val content: String,

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf<String>(
                title,
                "${title.first()}",
                content,
                "${content.first()}"
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}
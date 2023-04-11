package com.example.simplenotesapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity //tale name defaults to class name, in this case table name = Note
data class Note(

    val title: String,
    val content: String,

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
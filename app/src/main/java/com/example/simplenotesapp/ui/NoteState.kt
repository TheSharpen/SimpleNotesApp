package com.example.simplenotesapp.ui

import com.example.simplenotesapp.data.entity.Note

data class NoteState(
    val notes: List<Note> = emptyList(),
    val title: String = "",
    val content: String = "",
    val isAddingNote: Boolean = false

)

package com.example.simplenotesapp.ui

import com.example.simplenotesapp.data.entity.Note

data class NoteState(
    var id: Int = 0,
    val notes: List<Note> = emptyList(),
    var title: String = "",
    var content: String = "",
    val isAddingNote: Boolean = false

)

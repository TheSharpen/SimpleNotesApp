package com.example.simplenotesapp.ui

import com.example.simplenotesapp.data.entity.Note

sealed interface NoteEvent {

    object SaveNote : NoteEvent
    data class SetTitle(val title: String) : NoteEvent
    data class SetContent(val content: String) : NoteEvent
    object ShowDialog : NoteEvent
    object HideDialog : NoteEvent
    data class DeleteNote(val note: Note) : NoteEvent
}
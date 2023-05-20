package com.example.simplenotesapp.ui.main

import com.example.simplenotesapp.data.entity.Note

sealed interface NoteEvent {

    object SaveNote : NoteEvent


    data class SetTitle(val title: String) : NoteEvent
    data class SetContent(val content: String) : NoteEvent
    data class ResetSearchText(val text: String): NoteEvent
    data class ResetTitle(val text: String): NoteEvent
    data class ResetContent(val text: String): NoteEvent

    object ShowDialog : NoteEvent
    object HideDialog : NoteEvent
    data class DeleteNote(val note: Note) : NoteEvent

}
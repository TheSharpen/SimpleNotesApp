package com.example.simplenotesapp.ui

import androidx.lifecycle.ViewModel
import com.example.simplenotesapp.data.NoteDao
import androidx.lifecycle.viewModelScope
import com.example.simplenotesapp.data.entity.Note
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoteViewModel(
    private val dao: NoteDao
): ViewModel() {


    private val _notes = dao.getAllNotes().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(NoteState())
    val state = combine(_state, _notes) { state, notes ->
        state.copy(
                notes = notes
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())


    fun onEvent(event: NoteEvent) {
        when(event) {
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    dao.deleteNote(event.note)
                }
            }
            NoteEvent.HideDialog -> {
                _state.update {
                    it.copy(
                            isAddingNote = false
                    )
                }
            }
            NoteEvent.SaveNote -> {
                val title = state.value.title
                val content = state.value.content

                if(title.isBlank() || content.isBlank()) {
                    return
                }

                val note = Note(
                        title = title,
                        content = content
                )

                viewModelScope.launch {
                    dao.upsertNote(note)
                }

                _state.update { it.copy(
                        isAddingNote = false,
                        title = "",
                        content = ""
                ) }
            }



            is NoteEvent.SetContent -> {
                _state.update { it.copy(
                        content = event.content
                ) }
            }
            is NoteEvent.SetTitle -> {
                _state.update { it.copy(
                        title = event.title
                ) }
            }
            NoteEvent.ShowDialog -> {
                _state.update { it.copy(
                        isAddingNote = true
                ) }
            }
        }
    }

}
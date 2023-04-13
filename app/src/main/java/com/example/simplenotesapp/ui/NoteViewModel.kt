package com.example.simplenotesapp.ui

import androidx.lifecycle.ViewModel
import com.example.simplenotesapp.data.NoteDao
import androidx.lifecycle.viewModelScope
import com.example.simplenotesapp.data.entity.Note
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoteViewModel(
    private val dao: NoteDao,
) : ViewModel() {


    private val _notes =
        dao.getAllNotes().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(NoteState())
    val state = combine(_state, _notes) { state, notes ->
        state.copy(
                notes = notes
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())

    //Search function
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _searchNotes = MutableStateFlow(state.value.notes)
    val searchNotes = searchText.combine(_searchNotes) { text, searchNotes ->
        if (text.isBlank()) {
            searchNotes
        } else {
            searchNotes.filter {
                it.doesMatchSearchQuery(text)
            }
        }
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _searchNotes.value
    )

    fun Note.doesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf<String>(
                state.value.title,
                "${state.value.title.first()}",
                state.value.content,
                "${state.value.content.first()}"
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }


    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onEvent(event: NoteEvent) {
        when (event) {
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
                val id = state.value.id

                if (title.isBlank() || content.isBlank()) {
                    return
                }

                val note = if (id == 0) {
                    Note(
                            title = title, content = content
                    )
                } else {
                    Note(
                            title = title, content = content, id = id
                    )
                }



                viewModelScope.launch {
                    dao.upsertNote(note)
                }

                _state.update {
                    it.copy(
                            isAddingNote = false, title = "", content = ""
                    )
                }
            }


            is NoteEvent.SetContent -> {
                _state.update {
                    it.copy(
                            content = event.content
                    )
                }
            }
            is NoteEvent.SetTitle -> {
                _state.update {
                    it.copy(
                            title = event.title
                    )
                }
            }
            NoteEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                            isAddingNote = true
                    )
                }
            }
        }
    }

}
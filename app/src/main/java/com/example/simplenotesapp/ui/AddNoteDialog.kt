package com.example.simplenotesapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun AddNoteDialog(
    state: NoteState,
    onEvent: (NoteEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(modifier = modifier, onDismissRequest = { onEvent(NoteEvent.HideDialog) },
    title = {
        Text(text = "Add note")
    },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextField(value = state.title, onValueChange = {
                        onEvent(NoteEvent.SetTitle(it))
                    }, placeholder = { Text(text = "Note Title") })

                    TextField(value = state.content, onValueChange = {
                        onEvent(NoteEvent.SetContent(it))
                    }, placeholder = { Text(text = "Contents of your note") })


                }
            },
            buttons =  {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    Button(onClick =  {
                        onEvent(NoteEvent.SaveNote)
                    }) {
                        Text(text = "Save")
                    }
                }
            }
    )
}

package com.example.simplenotesapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AddNoteDetailScreen(
    state: NoteState,
    onEvent: (NoteEvent) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        TextField(value = state.title, onValueChange = {
            onEvent(NoteEvent.SetTitle(it))
        }, placeholder = { Text(text = "Note Title") })

        TextField(value = state.content, onValueChange = {
            onEvent(NoteEvent.SetContent(it))
        }, placeholder = { Text(text = "Contents of your note") })

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            Button(onClick =  {
                onEvent(NoteEvent.SaveNote)
                navController.popBackStack()
            }) {
                Text(text = "Save")

            }
        }


    }

}
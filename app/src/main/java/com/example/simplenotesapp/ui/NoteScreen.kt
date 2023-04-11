package com.example.simplenotesapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.simplenotesapp.util.Routes

@Composable
fun NoteScreen(
    state: NoteState,
    onEvent:  (NoteEvent) -> Unit,
    navController: NavController
) {


    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
        onEvent(NoteEvent.ShowDialog) }
        ) {
            Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add note")

        }
    }) {padding ->
        if(state.isAddingNote) {
            navController.navigate(Routes.ADD_NOTE_DETAIL_SCREEN)
           // AddNoteDialog(state = state, onEvent = onEvent  )
        }


        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(state.notes) { note ->
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = note.title, fontSize = 32.sp)
                    Spacer(modifier = Modifier.size(12.dp))
                    Text(text = note.content, fontSize = 20.sp)
                    
                }
                IconButton(onClick = { onEvent(NoteEvent.DeleteNote(note)) }) {
                    Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete note"
                    )

                }
                
            }

        }
        
    }

    }
}
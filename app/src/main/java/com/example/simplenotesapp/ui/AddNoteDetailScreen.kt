package com.example.simplenotesapp.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.simplenotesapp.data.entity.Note


@Composable
fun AddNoteDetailScreen(
    state: NoteState,
    onEvent: (NoteEvent) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
    context: Context,
    id: Int? = 0,
    title: String? = "",
    content: String? = "",
) {

    BackHandler() {
        navController.popBackStack()
        state.title = ""
        state.content = ""
    }

    if (id == null || id == 0) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {


            TextField(value = state.title, onValueChange = {
                onEvent(NoteEvent.SetTitle(it))
            }, placeholder = { Text(text = "Note Title") })

            TextField(value = state.content, onValueChange = {
                onEvent(NoteEvent.SetContent(it))
            }, placeholder = { Text(text = "Contents of your note") })

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                Button(onClick = {
                    onEvent(NoteEvent.SaveNote)
                    navController.popBackStack()
                }) {
                    Text(text = "Save")
                }
            }
        }

    } else {
        var varTitle = remember {
            mutableStateOf(title!!)
        }
        var varContent = remember {
            mutableStateOf(content!!)
        }


        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            TextField(value = varTitle.value, onValueChange = { it ->
                varTitle.value = it
            }, placeholder = { Text(text = "Note Title") })

            TextField(value = varContent.value, onValueChange = { it ->
                varContent.value = it
            }, placeholder = { Text(text = "Contents of your note") })




            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
            ) {


                Box(
                        modifier = Modifier.wrapContentWidth(),
                        contentAlignment = Alignment.CenterStart
                ) {
                    IconButton(
                            onClick = {

                                val note = Note(
                                        title = state.title, content = state.content, id = id
                                )

                                onEvent(NoteEvent.DeleteNote(note))
                                navController.popBackStack()
                            }, modifier = Modifier.size(64.dp)
                    ) {
                        Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete note",
                                tint = Color.Gray
                        )
                    }


                }

                Box(
                        modifier = Modifier.wrapContentWidth(),
                        contentAlignment = Alignment.CenterEnd
                ) {
                    Button(onClick = {

                        state.title = varTitle.value.toString()
                        state.content = varContent.value.toString()
                        state.id = id

                        onEvent(NoteEvent.SaveNote)
                        navController.popBackStack()
                    }) {
                        Text(text = "Save Changes")
                    }


                }


            }
        }
    }
}

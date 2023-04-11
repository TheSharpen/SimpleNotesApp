package com.example.simplenotesapp.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun AddNoteDetailScreen(
    state: NoteState,
    onEvent: (NoteEvent) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
    context: Context,
    id: Int? = 0 ,
    title: String? = "",
    content: String? = ""
) {

    if (id == null) {
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
            TextField(value = varTitle.value, onValueChange = {
               it -> varTitle.value = it
            }, placeholder = { Text(text = "Note Title") })

            TextField(value = varContent.value, onValueChange = { it ->
                varContent.value = it
            }, placeholder = { Text(text = "Contents of your note") })

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
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
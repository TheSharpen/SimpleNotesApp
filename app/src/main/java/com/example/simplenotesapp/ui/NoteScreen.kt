package com.example.simplenotesapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.simplenotesapp.util.Routes

@Composable
fun NoteScreen(
    state: NoteState,
    onEvent:  (NoteEvent) -> Unit,
    navController: NavController
) {

    val viewModel = viewModel<NoteViewModel>()
    //val searchText by viewModel.searchText.collectAsState("")



    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(Routes.ADD_NOTE_DETAIL_SCREEN)
        }
        ) {
            Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add note")

        }
    })
    {padding ->

        Column(modifier = Modifier.fillMaxWidth()) {
            TextField(value = , onValueChange = )
            
        }

        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFffff99)),
            verticalArrangement = Arrangement.spacedBy(16.dp),

                ) {
            items(state.notes) { note ->
                Card(
                        shape = RoundedCornerShape(8),
                        elevation = 8.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .clickable(onClick = {
                                navController.navigate(
                                        "${Routes.ADD_NOTE_DETAIL_SCREEN}/${note.id}/${note.title}/${note.content}"
                                )
                            }),

            ) {
                Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                                text = note.title,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                        )
                        Spacer(modifier = Modifier.size(12.dp))
                        Text(
                                text = note.content,
                                fontSize = 20.sp,
                                color = Color.Gray
                        )
                    }
                    IconButton(
                            onClick = { onEvent(NoteEvent.DeleteNote(note)) },
                            modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete note",
                                tint = Color.Red
                        )
                    }
                }
            }

        }
        
    }

    }
}
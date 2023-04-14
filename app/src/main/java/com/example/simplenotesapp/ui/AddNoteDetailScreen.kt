package com.example.simplenotesapp.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.simplenotesapp.data.entity.Note
import com.example.simplenotesapp.util.Routes


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

    //backgroundColor = Color(0xFFF3E99F)

    Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .background(Color(0xFFF3E99F))
                .fillMaxSize()
    ) {

        Card(
                shape = RoundedCornerShape(8),
                elevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                backgroundColor = Color.White,
        ) {


            Column(
                    verticalArrangement = Arrangement.Top, modifier = Modifier.fillMaxSize()

            ) {

                if (id == null || id == 0) {


                    TextField(
                            textStyle = TextStyle(
                                    fontSize = 24.sp,
                                    color = Color(0xFFF2A900),
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    textDecoration = TextDecoration.None
                            ),
                            value = state.title,
                            onValueChange = {
                                onEvent(NoteEvent.SetTitle(it))
                            },
                            placeholder = {
                                Text(
                                        text = "Note Title", style = TextStyle(
                                        fontSize = 24.sp,
                                        color = Color(0xFFA8A8A8),
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        textDecoration = TextDecoration.None
                                )
                                )
                            },

                            modifier = Modifier
                                .fillMaxWidth()
                                .border(width = 0.dp, color = Color.Transparent),
                    )

                    TextField(
                            value = state.content,
                            onValueChange = {
                                onEvent(NoteEvent.SetContent(it))
                            },
                            placeholder = {
                                Text(
                                        text = "Contents of your note", style = TextStyle(
                                        fontSize = 16.sp, color = Color(0xFFA8A8A8)
                                )
                                )
                            },

                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .border(width = 0.dp, color = Color.Transparent),
                    )

                    Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            contentAlignment = Alignment.Center
                    ) {
                        Button(onClick = {
                            onEvent(NoteEvent.SaveNote)
                            navController.popBackStack()
                        }) {
                            Text(text = "Save")
                        }
                    }


                } else {


                    var varTitle = remember {
                        mutableStateOf(title!!)
                    }
                    var varContent = remember {
                        mutableStateOf(content!!)
                    }

                    TextField(value = varTitle.value, onValueChange = { it ->
                        varTitle.value = it
                    }, placeholder = { Text(text = "Note Title") },

                            textStyle = TextStyle(
                                    fontSize = 24.sp,
                                    color = Color(0xFFF2A900),
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    textDecoration = TextDecoration.None
                            ),

                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                        width = 0.dp,
                                        color = Color.Transparent,
                                        shape = RoundedCornerShape(8.dp)
                                )
                    )

                    TextField(
                            value = varContent.value, onValueChange = { it ->
                        varContent.value = it
                    }, textStyle = TextStyle(
                            fontSize = 16.sp, color = Color.Black
                    ), modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .border(
                                width = 0.dp,
                                color = Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                        )

                    )


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
                                                title = state.title,
                                                content = state.content,
                                                id = id
                                        )

                                        onEvent(NoteEvent.DeleteNote(note))
                                        navController.popBackStack()
                                    }, modifier = Modifier.size(64.dp)
                            ) {
                                Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete note",
                                        tint = Color(0xFFF2A900),
                                        modifier = Modifier.size(32.dp)
                                )
                            }
                        }

                        Box(
                                modifier = Modifier.wrapContentWidth(),
                                contentAlignment = Alignment.CenterEnd
                        ) {
                            Button(
                                    onClick = {

                                        state.title = varTitle.value.toString()
                                        state.content = varContent.value.toString()
                                        state.id = id

                                        onEvent(NoteEvent.SaveNote)
                                        navController.popBackStack()
                                    }, colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFFF2A900)

                            )
                            ) {
                                Text(text = "Save Changes")
                            }


                        }
                    }


                }


            }
        }
    }
}



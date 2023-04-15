package com.example.simplenotesapp.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.simplenotesapp.R
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
        state.title = ""
        state.content = ""
        navController.popBackStack()
        Log.d("ALOG", "state = ${state.toString()}")

    }

    Log.d("ALOG", "state = ${state.toString()}")


    Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF3E99F)),
            contentAlignment = Alignment.Center
    ) {


        if (id == null || id == 0) {

            Column(
                    verticalArrangement = Arrangement.Top, modifier = Modifier.fillMaxSize()
            ) {

                Card(
                        shape = RoundedCornerShape(8),
                        elevation = 8.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.9f)
                            .padding(start = 24.dp, end = 24.dp, top = 24.dp),
                        backgroundColor = Color.White,
                ) {


                    Column(
                            verticalArrangement = Arrangement.Top, modifier = Modifier.fillMaxSize()

                    ) {


                        TextField(
                                textStyle = TextStyle(
                                        fontSize = 28.sp,
                                        color = Color(0xFFF2A900),
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Start,
                                        textDecoration = TextDecoration.None
                                ),
                                value = state.title,
                                onValueChange = {
                                    onEvent(NoteEvent.SetTitle(it))
                                },
                                placeholder = {
                                    Text(
                                            style = TextStyle(
                                                    fontSize = 24.sp,
                                                    color = Color(0xFFA8A8A8),
                                                    fontWeight = FontWeight.Bold,
                                                    textAlign = TextAlign.Start,
                                                    textDecoration = TextDecoration.None
                                            ),
                                            text = "Note Title",

                                            )
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = Color.Transparent,
                                        cursorColor = Color.Black,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent

                                ),

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(width = 0.dp, color = Color.Transparent),
                        )

                        LazyColumn(
                                modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                TextField(
                                        value = state.content,
                                        onValueChange = {
                                            onEvent(NoteEvent.SetContent(it))
                                        },
                                        colors = TextFieldDefaults.textFieldColors(
                                                backgroundColor = Color.Transparent,
                                                cursorColor = Color.Black,
                                                focusedIndicatorColor = Color.Transparent,
                                                unfocusedIndicatorColor = Color.Transparent

                                        ),

                                        placeholder = {
                                            Text(
                                                    text = "Contents of your note",
                                                    style = TextStyle(
                                                            fontSize = 16.sp,
                                                            color = Color(0xFFA8A8A8)
                                                    )
                                            )
                                        },


                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(width = 0.dp, color = Color.Transparent),
                                )
                            }
                        }
                    }
                }

                Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(0.1f),
                        contentAlignment = Alignment.Center
                ) {

                    IconButton(onClick = {

                        onEvent(NoteEvent.SaveNote)
                        navController.popBackStack()
                    }) {


                        Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_save),
                                contentDescription = "Icon Save",
                                modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        } else {

            Column(
                    verticalArrangement = Arrangement.Top, modifier = Modifier.fillMaxSize()
            ) {

                var varTitle = remember {
                    mutableStateOf(title!!)
                }
                var varContent = remember {
                    mutableStateOf(content!!)
                }

                Card(
                        shape = RoundedCornerShape(8),
                        elevation = 8.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.9f)
                            .padding(start = 24.dp, end = 24.dp, top = 24.dp),
                        backgroundColor = Color.White,
                ) {


                    Column(
                            verticalArrangement = Arrangement.Top, modifier = Modifier.fillMaxSize()

                    ) {


                        TextField(value = varTitle.value, onValueChange = { it ->
                            varTitle.value = it
                        }, placeholder = { Text(text = "Note Title") },

                                textStyle = TextStyle(
                                        fontSize = 28.sp,
                                        color = Color(0xFFF2A900),
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        textDecoration = TextDecoration.None
                                ), colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                cursorColor = Color.Black,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent

                        ),

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                            width = 0.dp,
                                            color = Color.Transparent,
                                            shape = RoundedCornerShape(8.dp)
                                    )
                        )



                        Column(
                                modifier = Modifier

                                    .fillMaxSize()
                        ) {


                            LazyColumn(
                                    modifier = Modifier

                                        .fillMaxWidth()
                                        .wrapContentHeight()
                            ) {
                                item {
                                    TextField(
                                            value = varContent.value, onValueChange = { it ->
                                        varContent.value = it
                                    }, textStyle = TextStyle(
                                            fontSize = 16.sp, color = Color.Black
                                    ), colors = TextFieldDefaults.textFieldColors(
                                            backgroundColor = Color.Transparent,
                                            cursorColor = Color.Black,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent

                                    ), modifier = Modifier
                                        .fillMaxSize()
                                        .border(
                                                width = 0.dp,
                                                color = Color.Transparent,
                                                shape = RoundedCornerShape(8.dp)
                                        )

                                    )
                                }

                            }
                        }

                    }

                }

                Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(0.1f),
                        horizontalArrangement = Arrangement.SpaceAround
                ) {


                    Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(0.5f),
                            contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                                onClick = {

                                    val note = Note(
                                            title = state.title, content = state.content, id = id
                                    )

                                    onEvent(NoteEvent.DeleteNote(note))
                                    navController.popBackStack()
                                },
                        ) {
                            Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete note",
                                    modifier = Modifier.size(48.dp)
                            )
                        }
                    }

                    Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(0.5f),
                            contentAlignment = Alignment.Center
                    ) {

                        IconButton(onClick = {
                            state.title = varTitle.value.toString()
                            state.content = varContent.value.toString()
                            state.id = id

                            onEvent(NoteEvent.SaveNote)
                            navController.popBackStack()

                        }) {
                            Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_save),
                                    contentDescription = "Icon Save",
                                    modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
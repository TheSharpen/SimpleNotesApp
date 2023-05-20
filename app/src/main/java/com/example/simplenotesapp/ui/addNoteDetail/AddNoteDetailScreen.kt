package com.example.simplenotesapp.ui

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
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
import com.example.simplenotesapp.ui.main.NoteEvent
import com.example.simplenotesapp.util.NoteState


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

    val dialogShown = remember { mutableStateOf(false) }
    val deleteDialogShown = remember { mutableStateOf(false) }



    BackHandler() {

        if (id == 0 && state.title.isNotEmpty() || state.content.isNotEmpty()) {
            dialogShown.value = true
        } else {
            navController.popBackStack()
            onEvent(NoteEvent.ResetSearchText(""))
            onEvent(NoteEvent.ResetTitle(""))
            onEvent(NoteEvent.ResetContent(""))
        }


    }

    if (dialogShown.value) {
        AlertDialog(onDismissRequest = { dialogShown.value = false }, title = {
            Text(
                    "Are you sure you want to discard new note without saving?",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
            )
        }, confirmButton = {

            Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                        onClick = { dialogShown.value = false },
                        colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Black.copy(0.85f)
                        )
                ) {
                    Text(text = "Cancel", color = Color.White)
                }
                Button(
                        onClick = {
                            dialogShown.value = false
                            navController.popBackStack()
                            onEvent(NoteEvent.ResetSearchText(""))
                            onEvent(NoteEvent.ResetTitle(""))
                            onEvent(NoteEvent.ResetContent(""))
                        }, colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black.copy(0.85f)
                )
                ) {
                    Text("Discard", color = Color.White)
                }
            }


        })
    }

    if (deleteDialogShown.value) {
        AlertDialog(onDismissRequest = { deleteDialogShown.value = false }, title = {
            Text(
                    "Are you sure you want to delete this note?",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
            )
        }, confirmButton = {

            Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                        onClick = { deleteDialogShown.value = false },
                        colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Black.copy(0.85f)
                        )
                ) {
                    Text(text = "Cancel", color = Color.White)
                }
                Button(
                        onClick = {

                            val note = Note(
                                    title = state.title, content = state.content, id = id!!
                            )


                            deleteDialogShown.value = false
                            navController.popBackStack()
                            onEvent(NoteEvent.DeleteNote(note))
                        }, colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black.copy(0.85f)
                )
                ) {
                    Text("Delete", color = Color.White)
                }
            }


        })
    }






    Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                        colorResource(id = R.color.yellowLight)),
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
                                        color = colorResource(id = R.color.yellowIntense),
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
                                                    color = colorResource(id = R.color.lightGray),
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
                                                            color = colorResource(id = R.color.lightGray)
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

            var varTitle = remember {
                mutableStateOf(title!!)
            }
            var varContent = remember {
                mutableStateOf(content!!)
            }

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


                        TextField(value = varTitle.value, onValueChange = { it ->
                            varTitle.value = it
                        }, placeholder = { Text(text = "Note Title") },

                                textStyle = TextStyle(
                                        fontSize = 28.sp,
                                        color = colorResource(id = R.color.yellowIntense),
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
                                    deleteDialogShown.value = true
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
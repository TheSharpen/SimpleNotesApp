package com.example.simplenotesapp.ui.main

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.simplenotesapp.util.NoteState
import com.example.simplenotesapp.util.Routes
import kotlinx.coroutines.launch
import kotlin.system.exitProcess


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteScreen(
    state: NoteState,
    onEvent: (NoteEvent) -> Unit,
    navController: NavController,
    viewModel: NoteViewModel,
) {
    val searchText = viewModel.searchText.collectAsState()
    val searchNotes = viewModel.searchNotes.collectAsState()
    val isSearching = viewModel.isSearching.collectAsState()

    val dialogShown = remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    val enterTransition = remember {
        slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(durationMillis = 300)
        )
    }
    val exitTransition = remember {
        slideOutVertically(
                targetOffsetY = { fullHeight -> -fullHeight },
                animationSpec = tween(durationMillis = 300)
        )
    }



    BackHandler() {
        dialogShown.value = true
    }

    if (dialogShown.value) {
        AlertDialog(onDismissRequest = { dialogShown.value = false }, title = {

            Text(
                    "Are you sure you want to quit?",
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
                                backgroundColor = MaterialTheme.colors.error
                        )
                ) {
                    Text(text = "Cancel", color = MaterialTheme.colors.onError)
                }
                Button(
                        onClick = {
                            dialogShown.value = false
                            exitProcess(0)
                        }, colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.error
                )
                ) {
                    Text("Quit", color = MaterialTheme.colors.onError)
                }
            }



        }, backgroundColor = MaterialTheme.colors.primary
        )
    }

    Scaffold(
            floatingActionButton = {
                FloatingActionButton(backgroundColor = MaterialTheme.colors.onSecondary.copy(alpha = 0.93f), onClick = {
                    navController.navigate(Routes.ADD_NOTE_DETAIL_SCREEN)
                }) {
                    Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add note",
                            tint = MaterialTheme.colors.secondaryVariant,
                            modifier = Modifier.size(40.dp)
                    )
                }
            },

            backgroundColor = MaterialTheme.colors.background
    ) { padding ->

        viewModel.update_searchNotes()

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(onPress = { focusManager.clearFocus() })
                detectDragGestures(onDrag = { _, _ ->
                    focusManager.clearFocus()
                })
            }

        ) {

            TextField(
                    value = searchText.value,
                    onValueChange = viewModel::onSearchTextChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = MaterialTheme.colors.surface)
                        .border(width = 0.dp, color = Color.Transparent),
                    leadingIcon = {
                        Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = MaterialTheme.colors.onError.copy(alpha = 0.7f),
                        )
                    },
                    trailingIcon = {
                        if (searchText.value != "" || searchText.value.isNotEmpty()) {
                            IconButton(onClick = {

                                onEvent(NoteEvent.ResetSearchText(""))
                            }) {


                                Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear query",
                                        tint = MaterialTheme.colors.onError.copy(alpha = 0.7f)
                                )
                            }


                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                            cursorColor = MaterialTheme.colors.onError.copy(alpha = 0.7f),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,


                            ),
                    textStyle = MaterialTheme.typography.h6.copy(
                            color = MaterialTheme.colors.onError.copy(alpha = 0.7f), fontWeight = FontWeight.Bold
                    ),
                    placeholder = {
                        Text(
                                text = "Search",
                                color = MaterialTheme.colors.onError.copy(alpha = 0.7f),
                                fontSize = 20.sp,
                        )
                    },
            )


            Spacer(modifier = Modifier.height(8.dp))
            if (isSearching.value) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                val scrollState = rememberScrollState()
                val coroutineScope = rememberCoroutineScope()

                LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .scrollable(scrollState, Orientation.Vertical)
                ) {
                    items(searchNotes.value) {
                        note ->
                        Card(
                                shape = RoundedCornerShape(8.dp),
                                elevation = 8.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                    .pointerInput(Unit) {
                                        detectTapGestures(onPress = { focusManager.clearFocus() })
                                        detectDragGestures(onDrag = { change, dragAmount ->
                                            coroutineScope.launch {
                                                scrollState.scrollBy(dragAmount.y)
                                            }
                                            focusManager.clearFocus()
                                            change.consume()
                                        })

                                    }
                                    .clickable(onClick = {
                                        navController.navigate(
                                                "${Routes.ADD_NOTE_DETAIL_SCREEN}/${note.id}/${note.title}/${note.content}"
                                        )
                                    }),
                                backgroundColor = MaterialTheme.colors.primary,
                        ) {
                            Column(
                                    modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                        text = note.title,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colors.primaryVariant
                                )
                                Spacer(modifier = Modifier.size(12.dp))
                                Text(
                                        text = note.content,
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colors.onPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

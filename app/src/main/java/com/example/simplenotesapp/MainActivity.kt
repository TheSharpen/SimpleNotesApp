package com.example.simplenotesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.simplenotesapp.data.NoteDatabase
import com.example.simplenotesapp.ui.AddNoteDetailScreen
import com.example.simplenotesapp.ui.NoteScreen
import com.example.simplenotesapp.ui.NoteViewModel
import com.example.simplenotesapp.ui.theme.SimpleNotesAppTheme
import com.example.simplenotesapp.util.Routes

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
                applicationContext,
                NoteDatabase::class.java,
                "notes.db"
        ).build()
    }

    //Was showing error overrides nothing - Making viewModel non-nullable resolved issue
    private  val viewModel  by viewModels<NoteViewModel>(
            factoryProducer = {
                object: ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return NoteViewModel(db.dao) as T
                    }
                }
            }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleNotesAppTheme {

                val state by viewModel.state.collectAsState()

                val navController = rememberNavController()

                NavHost(navController,
                        startDestination = Routes.NOTE_SCREEN) {
                    composable(Routes.NOTE_SCREEN) {
                        NoteScreen(state = state, onEvent = viewModel::onEvent, navController = navController)
                    }
                    composable(Routes.ADD_NOTE_DETAIL_SCREEN) {
                        AddNoteDetailScreen(state = state, onEvent = viewModel::onEvent)
                    }
                }


            }
        }
    }
}


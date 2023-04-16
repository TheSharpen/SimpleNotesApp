package com.example.simplenotesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.simplenotesapp.data.NoteDatabase
import com.example.simplenotesapp.ui.AddNoteDetailScreen
import com.example.simplenotesapp.ui.NoteScreen
import com.example.simplenotesapp.ui.NoteViewModel
import com.example.simplenotesapp.ui.SplashScreen
import com.example.simplenotesapp.ui.theme.SimpleNotesAppTheme
import com.example.simplenotesapp.util.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                        startDestination = Routes.SPLASH_SCREEN) {
                    composable(
                            Routes.NOTE_SCREEN
                    ) {
                        NoteScreen(
                                state = state,
                                onEvent = viewModel::onEvent,
                                navController = navController,
                                viewModel = viewModel
                        )
                    }
                    composable(Routes.ADD_NOTE_DETAIL_SCREEN) {
                        AddNoteDetailScreen(
                                state = state,
                                onEvent = viewModel::onEvent,
                                navController = navController,
                                context = applicationContext
                        )
                    }
                    composable(
                            Routes.ADD_NOTE_DETAIL_SCREEN_ARGS,
                            arguments = listOf(navArgument("id") { type = NavType.IntType },
                                    navArgument("title") { type = NavType.StringType },
                                    navArgument("content") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("id") ?: 0
                        val  title = backStackEntry.arguments?.getString("title") ?: ""
                        val  content = backStackEntry.arguments?.getString("content") ?: ""

                        AddNoteDetailScreen(
                                state = state,
                                onEvent = viewModel::onEvent,
                                navController = navController,
                                context = applicationContext,
                                id = id,
                                title = title,
                                content = content
                        )
                    }
                    composable(Routes.SPLASH_SCREEN) { SplashScreen(navController = navController)}
                }


            }
        }
    }
}




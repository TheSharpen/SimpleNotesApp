package com.example.simplenotesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.simplenotesapp.data.NoteDatabase
import com.example.simplenotesapp.ui.addNoteDetail.AddNoteDetailScreen
import com.example.simplenotesapp.ui.main.NoteScreen
import com.example.simplenotesapp.ui.main.NoteViewModel
import com.example.simplenotesapp.ui.splash.SplashScreen
import com.example.simplenotesapp.ui.theme.ComposeCustomThemingTheme
import com.example.simplenotesapp.util.Routes


class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
                applicationContext, NoteDatabase::class.java, "notes.db"
        ).build()
    }


    private val viewModel by viewModels<NoteViewModel>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return NoteViewModel(db.dao) as T
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ComposeCustomThemingTheme() {
                TransparentStatusBar()

                val state by viewModel.state.collectAsState()
                val navController = rememberNavController()

                NavHost(
                        navController, startDestination = Routes.SPLASH_SCREEN
                ) {
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
                        val title = backStackEntry.arguments?.getString("title") ?: ""
                        val content = backStackEntry.arguments?.getString("content") ?: ""

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
                    composable(Routes.SPLASH_SCREEN) { SplashScreen(navController = navController) }
                }


            }
        }
    }
}

@Composable
fun TransparentStatusBar() {
    val context = LocalContext.current
    val window = (context as ComponentActivity).window

    WindowCompat.setDecorFitsSystemWindows(window, false)

    val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
    windowInsetsController?.hide(WindowInsetsCompat.Type.statusBars())
    windowInsetsController?.systemBarsBehavior =
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    window.statusBarColor = Color.Transparent.toArgb()
}


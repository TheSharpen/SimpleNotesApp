package com.example.simplenotesapp.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.simplenotesapp.R
import com.example.simplenotesapp.util.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    navController: NavController
) {

    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            delay(1500)
            navController.navigate(Routes.NOTE_SCREEN)
        }
    }

    Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2A900))
    ) {
        Image(
                painter = painterResource(id = R.drawable.ic_note),
                contentDescription = "App Logo",
                modifier = Modifier.align(Alignment.Center).size(100.dp)
        )
    }
}
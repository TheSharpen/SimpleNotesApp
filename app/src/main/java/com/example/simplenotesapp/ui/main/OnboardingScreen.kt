package com.example.simplenotesapp.ui.main

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun OnboardingScreen(onDismiss: () -> Unit = {}) {


    Card(
            shape = RoundedCornerShape(15.dp),
            elevation = 8.dp,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
    ) {
        Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp)

        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(
                        text = "Welcome to         Simple Notes!",
                        style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(90.dp))
                Text(
                        text = "Capture and organize your thoughts.",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                        text = "Tap '+' to add a note.",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(30.dp))

                Text(
                        text = "Click a note to edit and save or delete.",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(30.dp))

                Text(
                        text = "Get started with Simple Notes now!",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(50.dp))
            }

            Column() {
                Button(onClick = { onDismiss() }, modifier = Modifier.padding(16.dp).height(50.dp).width(160.dp),colors = ButtonDefaults.buttonColors(Color(0xFFE1B827) ) ) {
                    Text(text = "Let me in!", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black)

                }
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}

fun shouldShowOnboarding(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("show_onboarding", true)
}

fun setOnboardingShown(context: Context) {
    val sharedPreferences = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
    sharedPreferences.edit().putBoolean("show_onboarding", false).apply()
}


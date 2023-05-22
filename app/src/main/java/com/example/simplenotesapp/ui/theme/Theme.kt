package com.example.simplenotesapp.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
        primary = Yellow700,
        onPrimary = Color.Black,
        primaryVariant = Gray700,
        background = DarkGray700,

        secondary = Teal200,
//        secondaryVariant = Color.Gray,
//        onSecondary = Color.Gray,
//        surface = Color.Gray,
//        error = Color.Gray,
//        onBackground = Color.Gray,
//        onSurface = Color.Gray,
//        onError = Color.Gray

)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
        primary = Color.White,
        onPrimary = Color.Black,
        primaryVariant = Gray700,
        background = LightGray200,

        secondary = Teal200,


//        secondaryVariant = Color.Gray,
//        onSecondary = Color.Gray,
//        surface = Color.Gray,
//        error = Color.Gray,
//        onBackground = Color.Gray,
//        onSurface = Color.Gray,
//        onError = Color.Gray

)

@Composable
fun ComposeCustomThemingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
    )
}

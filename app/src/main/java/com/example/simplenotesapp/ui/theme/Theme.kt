package com.example.simplenotesapp.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
        background = Gray700,
        primary = Yellow200,
        onPrimary = Gray300,
        primaryVariant = Gray700,
        secondary = Yellow200,
        secondaryVariant = DarkerYellow700 ,
        onSecondary = White,
        surface = WarmGray200.copy(alpha = 0.3f),
        onSurface = Gray500,
        error = Black,
        onError = White,

        onBackground = Gray100

)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
        background = WarmGray200,
        primary = LightGray200,
        onPrimary = Gray300,
        primaryVariant = DarkerYellow700,
        secondary = Gray300,
        secondaryVariant = DarkerYellow700 ,
        onSecondary = Gray500.copy(alpha = 0.8f),
        surface = LightGray200.copy(alpha = 0.98f),
        onSurface = Gray500,
        error = White,
        onError = Black,

        onBackground = Gray100
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

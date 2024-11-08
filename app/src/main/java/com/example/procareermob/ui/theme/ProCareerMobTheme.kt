package com.example.procareermob.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Определяем основные цвета
private val LightColorPalette = lightColors(
    primary = Color(0xFF3A6EFF),    // Синий цвет, используемый в логотипе и других элементах
    primaryVariant = Color(0xFF3451FF),
    secondary = Color(0xFFFF4081),   // Розовый цвет для иконок и текстовых ссылок
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

private val DarkColorPalette = darkColors(
    primary = Color(0xFF3A6EFF),
    primaryVariant = Color(0xFF3451FF),
    secondary = Color(0xFFFF4081),
    background = Color(0xFF121212),
    surface = Color(0xFF121212),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

// Создаем тему приложения
@Composable
fun ProCareerMobTheme(
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
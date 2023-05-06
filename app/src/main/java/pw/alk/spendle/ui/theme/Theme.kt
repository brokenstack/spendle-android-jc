package pw.alk.spendle.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = Black200,
    primaryVariant = primaryVariant,
    secondary = Teal200,

    background = Black500,
    surface = Black,

    onPrimary = Black,
    onSecondary = White,
    onBackground = Color.White,
    onSurface = Color.Black,
)

private val LightColorPalette = lightColors(
    primary = Black500,
    primaryVariant = Black700,
    secondary = Teal200
)

@Composable
fun SpendleTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        DarkColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
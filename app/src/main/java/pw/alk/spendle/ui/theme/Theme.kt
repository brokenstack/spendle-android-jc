package pw.alk.spendle.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Black200,
    primaryVariant = Black700,
    secondary = Teal200,
    onBackground = Color.White,
    background = Black700,
    onPrimary = Color.White
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
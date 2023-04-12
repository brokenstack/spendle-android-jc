package pw.alk.spendle.ui.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import pw.alk.spendle.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val drawableId: Int
) {
    object Home : Screen("home", R.string.home, R.drawable.house_line)
    object Stats : Screen("stats", R.string.stats, R.drawable.chart_line)
    object Settings : Screen("settings", R.string.settings, R.drawable.gear_line)
}
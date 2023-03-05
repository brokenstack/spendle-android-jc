package pw.alk.spendle.ui.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pw.alk.spendle.ui.screens.auth.AuthHome
import pw.alk.spendle.ui.screens.auth.AuthLogin
import pw.alk.spendle.ui.screens.auth.AuthRegister

sealed class Screen(val route: String) {
    object AuthHome : Screen(route = "auth_screen")
    object AuthLogin : Screen(route = "auth_login")
    object AuthRegister : Screen(route = "auth_register")
}

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.AuthHome.route) {
        composable(route = Screen.AuthHome.route) {
            AuthHome(navController)
        }

        composable(route = Screen.AuthRegister.route) {
            AuthRegister(navController)
        }

        composable(route = Screen.AuthLogin.route) {
            AuthLogin(navController)
        }
    }
}
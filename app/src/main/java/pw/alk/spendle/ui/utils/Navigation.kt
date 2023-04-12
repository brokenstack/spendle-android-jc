package pw.alk.spendle.ui.utils

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pw.alk.spendle.ui.screens.auth.AuthHome
import pw.alk.spendle.ui.screens.auth.NavigationViewModel
import pw.alk.spendle.ui.screens.auth.home.HomeScreen
import pw.alk.spendle.ui.screens.auth.login.LoginScreen
import pw.alk.spendle.ui.screens.auth.register.RegisterScreen
import pw.alk.spendle.ui.screens.auth.register.RegisterViewModel


sealed class Screen(val route: String) {
    object AuthHome : Screen(route = "auth_screen")
    object AuthLogin : Screen(route = "auth_login")
    object AuthRegister : Screen(route = "auth_register")
    object Home : Screen(route = "home")
}

@Composable
fun SetupNavGraph(navController: NavHostController, navViewModel: NavigationViewModel) {
    NavHost(navController = navController, startDestination = Screen.AuthHome.route) {
        composable(route = Screen.AuthHome.route) {
            AuthHome(navController, navViewModel)
        }

        composable(route = Screen.AuthRegister.route) {
            val viewModel = hiltViewModel<RegisterViewModel>()
            RegisterScreen(navController, viewModel)
        }

        composable(route = Screen.AuthLogin.route) {
            LoginScreen(navController)
        }

        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }
    }
}
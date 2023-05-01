package pw.alk.spendle.ui.nav

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import pw.alk.spendle.ui.screens.auth.AuthHome
import pw.alk.spendle.ui.screens.auth.NavigationViewModel
import pw.alk.spendle.ui.screens.auth.login.LoginScreen
import pw.alk.spendle.ui.screens.auth.login.LoginViewModel
import pw.alk.spendle.ui.screens.auth.register.RegisterScreen
import pw.alk.spendle.ui.screens.auth.register.RegisterViewModel
import pw.alk.spendle.ui.screens.main.MainScreen

sealed class AuthScreen(val route: String) {
    object Home : AuthScreen(route = "auth_home")
    object Login : AuthScreen(route = "auth_login")
    object Register : AuthScreen(route = "auth_register")
    object ForgotPassword : AuthScreen(route = "auth_forgot_pass")
}

fun NavGraphBuilder.authNavigationGraph(navController: NavController) {
    navigation(
        route = Graph.AUTH,
        startDestination = AuthScreen.Home.route
    ) {
        composable(route = AuthScreen.Home.route) {
            val viewModel = hiltViewModel<NavigationViewModel>()
            AuthHome(navController, viewModel)
        }

        composable(route = AuthScreen.Register.route) {
            val viewModel = hiltViewModel<RegisterViewModel>()
            RegisterScreen(navController, viewModel)
        }

        composable(route = AuthScreen.Login.route) {
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(navController, viewModel)
        }

        composable(route = Graph.MAIN) {
            MainScreen()
        }
    }
}
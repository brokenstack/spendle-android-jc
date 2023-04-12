package pw.alk.spendle.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pw.alk.spendle.ui.screens.auth.home.HomeScreen

object Graph {
    const val ROOT = "root_graph"
    const val AUTH = "auth_graph"
    const val HOME = "home_graph"
}

@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Graph.AUTH, route = Graph.ROOT) {
        authNavigationGraph(navController = navController)
        composable(route = Graph.HOME) {
            HomeScreen()
        }
    }
}
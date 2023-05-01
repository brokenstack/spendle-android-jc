package pw.alk.spendle.ui.screens.main

import android.util.Log
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pw.alk.spendle.ui.nav.BottomNavScreen
import pw.alk.spendle.ui.nav.MainNavigationGraph

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    val items = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Stats,
        BottomNavScreen.Settings
    )

    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    Log.e("MAIN", navBackStackEntry.toString())
    showBottomBar = when (navBackStackEntry?.destination?.route) {
        "add_expense" -> false
        else -> true
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar)
                BottomNavigation {
                    val currentDestination = navBackStackEntry?.destination

                    items.forEach { screen ->
                        BottomNavigationItem(
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            label = { Text(stringResource(id = screen.resourceId)) },
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                Icon(
                                    painterResource(id = screen.drawableId),
                                    contentDescription = screen.route
                                )
                            }
                        )
                    }
                }
        }) { innerPadding ->
        MainNavigationGraph(navController = navController, innerPadding = innerPadding)
    }
}

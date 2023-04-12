package pw.alk.spendle

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import pw.alk.spendle.ui.nav.RootNavigationGraph
import pw.alk.spendle.ui.theme.SpendleTheme

@HiltAndroidApp
class Spendle : Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpendleTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    navController = rememberNavController()
                    RootNavigationGraph(navController = navController)
                }
            }
        }
    }
}
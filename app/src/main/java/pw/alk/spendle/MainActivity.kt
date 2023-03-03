package pw.alk.spendle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pw.alk.spendle.ui.theme.SpendleTheme
import pw.alk.spendle.ui.utils.setupNavGraph

class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpendleTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    navController = rememberNavController()
                    setupNavGraph(navController = navController)
                }
            }
        }
    }
}
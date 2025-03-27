package com.example.emuapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.emuapp.api.dataOffline
import com.example.emuapp.data.Item
import com.example.emuapp.model.AuthModel
import com.example.emuapp.screens.AllScreens
import com.example.emuapp.screens.Cart
import com.example.emuapp.screens.Contact
import com.example.emuapp.screens.FAQ
import com.example.emuapp.screens.Home
import com.example.emuapp.screens.ItemView
import com.example.emuapp.screens.Items
import com.example.emuapp.screens.LogIn
import com.example.emuapp.screens.Notifications
import com.example.emuapp.screens.Profile
import com.example.emuapp.screens.Register
import com.example.emuapp.screens.Settings
import com.example.emuapp.ui.theme.EmuAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        var keepSplashScreen = true
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition{keepSplashScreen}
        lifecycleScope.launch {
            delay(5000)
            keepSplashScreen = false
        }
        enableEdgeToEdge()
        val authModel: AuthModel by viewModels()
        setContent {
            EmuAppTheme {
                AppNavigation(authModel)
            }
        }
    }
}


@Composable
fun AppNavigation(authModel:AuthModel){
    val navController = rememberNavController()
    NavHost(navController=navController, startDestination = AllScreens.LogIn.route) {
        composable(route=AllScreens.Home.route){
            Home(navController = navController, authModel=authModel)
        }
        composable(route=AllScreens.Items.route){
            Items(navController = navController)
        }
        composable(route=AllScreens.ItemView.route){
            navController.previousBackStackEntry?.savedStateHandle?.get<ArrayList<Item>>("item")
                ?.let { it1 ->
                    ItemView(
                        navController = navController,
                        item = it1
                    )
                }

        }
        composable(route=AllScreens.Profile.route){
            Profile(navController=navController)
        }
        composable(route=AllScreens.Settings.route){
            Settings(navController=navController)
        }
        composable(route=AllScreens.FAQ.route){
            FAQ(navController=navController)
        }
        composable(route=AllScreens.Contact.route){
            Contact(navController=navController)
        }
        composable(route=AllScreens.Notifications.route){
            Notifications(navController=navController)
        }
        composable(route=AllScreens.Register.route){
            Register(navController=navController, authModel = authModel)
        }
        composable(route=AllScreens.LogIn.route){
            LogIn(navController = navController,authModel=authModel)
        }
        composable(route=AllScreens.Cart.route){
            navController.previousBackStackEntry?.savedStateHandle?.get<ArrayList<Item>>("items")
                ?.let { it1 ->
                    Cart(navController = navController,
                        items = it1
                    )
                }
        }
    }

}

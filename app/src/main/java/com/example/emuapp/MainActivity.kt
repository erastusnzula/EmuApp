package com.example.emuapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.emuapp.data.Item
import com.example.emuapp.screens.AllScreens
import com.example.emuapp.screens.Contact
import com.example.emuapp.screens.FAQ
import com.example.emuapp.screens.Home
import com.example.emuapp.screens.ItemView
import com.example.emuapp.screens.Items
import com.example.emuapp.screens.Notifications
import com.example.emuapp.screens.Profile
import com.example.emuapp.screens.Settings
import com.example.emuapp.ui.theme.EmuAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EmuAppTheme {
                AppNavigation()
            }
        }
    }
}


@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController=navController, startDestination = AllScreens.Home.route) {
        composable(route=AllScreens.Home.route){
            Home(navController = navController)
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
    }

}

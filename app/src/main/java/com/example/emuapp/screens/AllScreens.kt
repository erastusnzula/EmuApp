package com.example.emuapp.screens

sealed class AllScreens(val route: String) {
    data object Home: AllScreens(route="home")
    data object Items: AllScreens(route="items")
    data object ItemView: AllScreens(route="item")
    data object Profile: AllScreens("profile")
    data object Settings: AllScreens("settings")
    data object Contact: AllScreens("contact")
    data object FAQ: AllScreens("FAQ")
    data object Register: AllScreens("register")
    data object LogIn: AllScreens("log-in")
    data object Notifications: AllScreens("notifications")
    data object Cart: AllScreens("Cart")

}
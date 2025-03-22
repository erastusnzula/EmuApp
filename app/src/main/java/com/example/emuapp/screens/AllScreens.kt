package com.example.emuapp.screens

sealed class AllScreens(val route: String) {
    data object Home: AllScreens(route="home")
    data object Items: AllScreens(route="items")
    data object ItemView: AllScreens(route="item")
}
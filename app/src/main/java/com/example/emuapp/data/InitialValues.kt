package com.example.emuapp.data

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf

object InitialValues {
    val error = mutableStateOf("Error Message Appears Here")
    val snackBarMessage = mutableStateOf("")
    val favouriteList = ArrayList<Item>()
}
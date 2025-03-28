package com.example.emuapp.data

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

object InitialValues {
    val error = mutableStateOf("Fetching ...")
    val snackBarMessage = mutableStateOf("")

    var fetchedItems = ArrayList<Item>()
    val favouriteList = ArrayList<Item>()
    val topBarIcon = mutableStateOf<Uri?>(null)
    val cartItems = mutableStateOf(ArrayList<Item>())
}
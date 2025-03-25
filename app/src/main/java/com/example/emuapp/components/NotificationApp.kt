package com.example.emuapp.components

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.core.app.NotificationCompat
import com.example.emuapp.R
import kotlin.random.Random

class NotificationApp:Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        val notificationChannel = NotificationChannel(
            "1",
            "Murstech",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
}

class NotificationBody(private val context: Context, private val message: String, private val title: String){
    private val notificationManager = context.getSystemService(NotificationManager::class.java)
    private val notificationChannelID = "1"
    fun showNotification(){
        val builder= NotificationCompat.Builder(context,notificationChannelID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.play_store_512)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(Random.nextInt(),builder)
    }


}
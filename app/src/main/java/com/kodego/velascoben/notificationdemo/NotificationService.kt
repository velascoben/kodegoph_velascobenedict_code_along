package com.kodego.velascoben.notificationdemo

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class NotificationService (private val context: Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification() {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            0
        )

        val notification = NotificationCompat.Builder(context, "sample_notification")
            .setSmallIcon(R.drawable.ic_android)
            .setContentTitle("This is a sample notification")
            .setContentText("Test Notification")
            .setContentIntent(activityPendingIntent)
            //.addAction(R.drawable.ic_android,"Increment",incrementIntent)
            .build()

        notificationManager.notify(
            1,
            notification
        )
    }
}
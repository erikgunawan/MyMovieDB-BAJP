package id.ergun.mymoviedb.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import id.ergun.mymoviedb.R
import id.ergun.mymoviedb.ui.view.home.HomeActivity

/**
 * Created by alfacart on 15/04/22.
 */
class NotificationHelper(val context: Context) {

    companion object {
        const val CHANNEL_ID = "mymoviedb_notification_channel"
        const val CHANNEL_NAME = "MyMovieDB Notification Channel"
        const val NOTIFICATION_ID = 1
    }

    fun createNotification() {
        val manager = NotificationManagerCompat.from(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(createNotificationChannel())
        }

        val contentIntent = Intent(context.applicationContext, HomeActivity::class.java)

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        else PendingIntent.FLAG_ONE_SHOT
        val contentPendingIntent = PendingIntent.getActivity(
            context.applicationContext,
            NOTIFICATION_ID,
            contentIntent,
            pendingIntent
        )

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_reminder)
            .setContentTitle("My MovieDB Daily Reminder")
            .setContentText("Check for the latest movie or tv show")
            .setContentIntent(contentPendingIntent)
            .setAutoCancel(true)

        manager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): NotificationChannel {
        return NotificationChannel(
            CHANNEL_ID, CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
    }

}
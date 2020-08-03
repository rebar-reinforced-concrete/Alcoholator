package com.mishaismenska.hackatonrsschoolapp.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mishaismenska.hackatonrsschoolapp.R

const val CHANNEL_ID = "sober_chanel"
const val NOTIFICATION_ID = 13

class ScheduledBroadcastReceiver : BroadcastReceiver() {

    private fun createNotificationChanel(context: Context){
        val name = context.getString(R.string.sober_channel_name)
        val descriptionText = context.getString(R.string.sober_chanel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun getStartActivityPendingIntent(context: Context) : PendingIntent{
        val startMainActivityIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivity(context, 0, startMainActivityIntent, 0)
    }

    private fun getNotificationBuilder(context: Context, pendingIntent: PendingIntent): NotificationCompat.Builder{
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_local_bar_24)
            .setContentTitle(context.getString(R.string.sober_notification_title))
            .setContentText(context.getString(R.string.sober_notification_content))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
    }

    override fun onReceive(context: Context, intent: Intent) {
        val startActivityPendingIntent = getStartActivityPendingIntent(context)
        val builder = getNotificationBuilder(context, startActivityPendingIntent)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChanel(context)
            builder.setChannelId(CHANNEL_ID)
        }

        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }
}

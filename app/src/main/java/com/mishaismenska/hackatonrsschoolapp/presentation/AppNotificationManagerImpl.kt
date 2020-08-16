package com.mishaismenska.hackatonrsschoolapp.presentation

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.mishaismenska.hackatonrsschoolapp.presentation.interfaces.AppNotificationManager
import java.time.Duration
import javax.inject.Inject

class AppNotificationManagerImpl @Inject constructor(context: Context) : AppNotificationManager {

    private val alarmManager by lazy { (context.getSystemService(Context.ALARM_SERVICE) as AlarmManager) }
    private val pendingIntent: PendingIntent by lazy {
        PendingIntent.getBroadcast(
            context,
            1,
            Intent(context, ScheduledBroadcastReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    override fun scheduleBecameSoberNotification(duration: Duration){
        val timeToTrigger = System.currentTimeMillis() + duration.toMillis()
        alarmManager.cancel(pendingIntent)
        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(
                timeToTrigger,
                pendingIntent
            ), pendingIntent
        )
    }

    override fun resetAllNotifications() {
        alarmManager.cancel(pendingIntent)
    }
}

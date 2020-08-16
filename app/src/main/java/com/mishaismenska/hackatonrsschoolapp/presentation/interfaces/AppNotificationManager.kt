package com.mishaismenska.hackatonrsschoolapp.presentation.interfaces

import java.time.Duration

interface AppNotificationManager {
    fun scheduleBecameSoberNotification(duration: Duration)
    fun resetAllNotifications()
}

package com.mishaismenska.hackatonrsschoolapp.presentation.interfaces

import java.time.Duration

interface AppNotificationManager {
    fun scheduleSoberNotification(duration: Duration)
}

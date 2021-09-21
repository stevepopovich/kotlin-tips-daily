package com.popovich.kotlintipsdaily

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

class TipsNotificationService {
    companion object {
        fun scheduleNotification(context: Context) {
            val currentDate = Calendar.getInstance()
            val dueDate = Calendar.getInstance()
            // Set Execution around 09:00:00 AM
            dueDate.set(Calendar.HOUR_OF_DAY, 9)
            dueDate.set(Calendar.MINUTE, 0)
            dueDate.set(Calendar.SECOND, 0)
            if (dueDate.before(currentDate)) {
                dueDate.add(Calendar.HOUR_OF_DAY, 24)
            }
            val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis

            val tipWorkRequest =
                OneTimeWorkRequestBuilder<TipWorker>()
                    .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
                    .build()
            val workManager = WorkManager.getInstance(context)
            workManager.cancelAllWork()
            workManager.enqueue(tipWorkRequest)
        }
    }
}

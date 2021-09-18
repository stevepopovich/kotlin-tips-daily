package com.popovich.kotlintipsdaily

import android.content.Context
import android.net.Uri
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.*

class TipsService {
    companion object {
        fun getNewRandomTip(): Tip {
            return Tip(
                UUID.randomUUID(),
                "Hi My Name is Rick",
                "Rick?",
                Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
            )
        }

        fun scheduleNotification(context: Context) {
            val tipWorkRequest =
                OneTimeWorkRequestBuilder<TipWorker>()
                    .build()
            val workManager = WorkManager.getInstance(context)
            workManager.cancelAllWork()
            workManager.enqueue(tipWorkRequest)
        }
    }




}
package com.popovich.kotlintipsdaily

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.popovich.kotlintipsdaily.database.TipService
import kotlin.random.Random

const val CHANNEL_ID = "kotlin_tips_daily"

class TipWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    private val notificationManager = context.getSystemService<NotificationManager>()!!

    override suspend fun doWork(): Result {
        val randomTip = TipService.getRandomTipNotDisplayed()
        randomTip?.let {
            createNotificationChannel(applicationContext)
            createNotification(applicationContext, it)
            TipsNotificationService.scheduleNotification(applicationContext)
            TipService.markTipAsDisplayed(it)
        }

        return Result.success()
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(context: Context, tip: Tip) {
        var builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(tip.title)
            .setContentText(tip.content)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val resultIntent = Intent(Intent.ACTION_VIEW)
        resultIntent.data = tip.link
        val pending = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_ONE_SHOT)
        builder.setContentIntent(pending)

        notificationManager.notify(
            Random.nextInt(),
            builder.build()
        )
    }
}

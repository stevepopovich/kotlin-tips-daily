package com.popovich.kotlintipsdaily.database

import android.content.Context
import android.net.Uri
import com.popovich.kotlintipsdaily.TipsNotificationService
import com.popovich.kotlintipsdaily.database.firebaserealtimedatabase.FirebaseRealtimeDatabase
import com.popovich.kotlintipsdaily.database.room.AppDatabase
import com.popovich.kotlintipsdaily.database.room.TipEntity
import kotlinx.coroutines.runBlocking

class TipSyncService {
    companion object {
        fun start(context: Context) {
            FirebaseRealtimeDatabase.setupTipsListener(context)
        }

        fun syncTips(firebaseTips: Map<String, HashMap<String, String>>, context: Context) {
            runBlocking {
                val tipDao = AppDatabase.database.tipDao()
                val allSyncedTipIds = tipDao.getAllIds()
                val notSyncedFirebaseTips = firebaseTips.entries.filter { !allSyncedTipIds.contains(it.key) }

                tipDao.insertAll(notSyncedFirebaseTips.mapNotNull { it.toRoomEntity })
                TipsNotificationService.scheduleNotification(context)
            }
        }
    }
}

private val Map.Entry<String, HashMap<String, String>>.toRoomEntity: TipEntity?
    get() {
        // Intentional validation,
        // we want to ignore if bad database found its way into firebase
        // This check is NOT useless
        if (
            this.key !is String ||
            this.value !is HashMap<String, String>
        ) return null

        val title = this.value["title"]
        val content = this.value["content"]
        val link = this.value["link"]

        return if (title != null &&
            content != null &&
            link != null &&
            Uri.parse(link) != null
        ) {
            TipEntity(
                id = this.key,
                title = title,
                content = content,
                link = link,
                hasBeenDisplayed = false
            )
        } else null
    }

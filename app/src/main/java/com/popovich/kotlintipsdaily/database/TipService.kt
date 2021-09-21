package com.popovich.kotlintipsdaily.database

import android.net.Uri
import com.popovich.kotlintipsdaily.Tip
import com.popovich.kotlintipsdaily.database.room.AppDatabase
import com.popovich.kotlintipsdaily.database.room.TipEntity
import java.util.UUID

class TipService {
    companion object {
        suspend fun getRandomTipNotDisplayed(): Tip? {
            val randomTip =
                AppDatabase.database
                    .tipDao()
                    .getRandomTipNotDisplayed()

            return randomTip?.let { it.toTip }
        }

        suspend fun markTipAsDisplayed(tip: Tip) {
            AppDatabase.database
                .tipDao()
                .update(tip.toDisplayedTipEntity)
        }
    }
}

val TipEntity.toTip: Tip
    get() = Tip(
        id = UUID.fromString(this.id),
        content = this.content,
        title = this.title,
        link = Uri.parse(this.link)
    )

val Tip.toDisplayedTipEntity: TipEntity
    get() = TipEntity(
        id = this.id.toString(),
        content = this.content,
        title = this.title,
        link = this.link.toString(),
        hasBeenDisplayed = true
    )

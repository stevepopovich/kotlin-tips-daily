package com.popovich.kotlintipsdaily.firebaserealtimedatabase

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class TipFirebaseModel(
    val content: String? = null,
    val title: String? = null,
    val link: String? = null,
)
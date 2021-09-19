package com.popovich.kotlintipsdaily.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TipEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "link") val link: String,
    @ColumnInfo(name = "hasBeenDisplayed") val hasBeenDisplayed: Boolean
)

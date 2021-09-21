package com.popovich.kotlintipsdaily.database.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TipEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tipDao(): TipDao

    companion object {
        lateinit var database: AppDatabase
    }
}

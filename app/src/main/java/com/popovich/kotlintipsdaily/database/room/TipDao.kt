package com.popovich.kotlintipsdaily.database.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TipDao {
    @Query("select * from tipentity where hasBeenDisplayed = '0' order by random() limit 1")
    suspend fun getRandomTipNotDisplayed(): TipEntity?

    @Query("select id from tipentity")
    suspend fun getAllIds(): List<String>

    @Update
    suspend fun update(tip: TipEntity)

    @Insert
    suspend fun insertAll(tips: List<TipEntity>)
}

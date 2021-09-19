package com.popovich.kotlintipsdaily.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TipDao {
    @Query("select * from tipentity where hasBeenDisplayed = 'false' limit 1")
    fun getRandomTipNotDisplayed(): TipEntity?

    @Update
    fun update(tip: TipEntity)

    @Insert
    fun insertAll(vararg tips: TipEntity)
}
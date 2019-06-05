package com.mario.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mario.persistence.RoomContract.Companion.SELECT_NUMBER
import com.mario.persistence.room.NumberDB

/**
 * Created by mariolopez on 28/12/17.
 */
@Dao
interface NumbersDao {

    @Query(SELECT_NUMBER)
    suspend fun getAllNumbers(): List<NumberDB>

    @Insert
    fun insertAll(vararg user: NumberDB)

}
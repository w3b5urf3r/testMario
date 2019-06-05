package com.mario.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amitshekhar.DebugDB
import com.mario.persistence.room.NumberDB

@Database(entities = [(NumberDB::class)], version = 1, exportSchema = true)
abstract class RoomNumbersDataSource : RoomDatabase() {

    abstract fun numbersDao(): NumbersDao

    companion object {

        fun buildPersistentSample(context: Context): RoomNumbersDataSource = Room.databaseBuilder(
                context.applicationContext,
                RoomNumbersDataSource::class.java,
                RoomContract.DATABASE_NUMBERS)
                .build()
                .also { if (BuildConfig.DEBUG) DebugDB.getAddressLog() }
    }
}
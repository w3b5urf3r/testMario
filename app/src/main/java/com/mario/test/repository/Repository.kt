package com.mario.test.repository

import com.mario.persistence.RoomNumbersDataSource
import com.mario.persistence.room.NumberDB
import com.mario.test.repository.remote.RemoteDataSource
import org.kodein.di.generic.instance

class Repository : BaseRepository() {
    private val remoteDataSource by instance<RemoteDataSource>()
    private val dbDataSource by instance<RoomNumbersDataSource>()

    override suspend fun getDefaultNumbers(): List<Int> = remoteDataSource.getDefaultNumbersAsync().await()
    override suspend fun addNumbers(vararg numbers: Int) =
        numbers.map { NumberDB(number = it) }.forEach { dbDataSource.numbersDao().insertAll(it) }

    override suspend fun getNumbers(): List<Int> =
        dbDataSource.numbersDao().getAllNumbers().map { it.number }


}


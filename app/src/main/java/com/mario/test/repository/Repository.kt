package com.mario.test.repository

import com.mario.test.repository.remote.RemoteDataSource
import org.kodein.di.generic.instance

class Repository : BaseRepository() {
    private val remoteDataSource by instance<RemoteDataSource>()

    override suspend fun getDefaultNumbers(): List<Int> = remoteDataSource.getDefaultNumbersAsync().await()

}


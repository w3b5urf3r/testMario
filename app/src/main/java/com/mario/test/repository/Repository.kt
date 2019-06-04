package com.mario.test.repository

import com.mario.test.repository.remote.RemoteDataSource
import org.kodein.di.generic.instance

/**
 * Created by mariolopez on 27/12/17.
 */
class Repository : BaseRepository() {
    private val remoteDataSource by instance<RemoteDataSource>()
//    val compositeDisposable: CompositeDisposable = CompositeDisposable()

//    suspend fun addDummyData() = remoteDataSource.getSomething()


}


package com.mario.test.di

import android.content.Context
import com.mario.test.App
import com.mario.test.repository.Repository
import com.mario.test.repository.remote.RemoteDataSource
import com.mario.test.util.Constants
import com.mario.persistence.RoomNumbersDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.*


object MainComponent {

    val appModule = Kodein.Module("app module") {
        bind<Context>() with provider { App.context }
        bind<Constants>() with singleton { Constants(this.instance()) }
        bind<Repository>() with provider { Repository() }
        bind<RemoteDataSource>() with provider { RemoteDataSource() }
        bind<RoomNumbersDataSource>() with eagerSingleton { RoomNumbersDataSource.buildPersistentSample(instance()) }
    }
}
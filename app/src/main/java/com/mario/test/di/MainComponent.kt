package com.mario.test.di

import android.content.Context
import com.mario.test.App
import com.mario.test.repository.Repository
import com.mario.test.repository.remote.RemoteDataSource
import com.mario.test.util.Constants
import com.mario.test.util.RxBus
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


object MainComponent {

    val appModule = Kodein.Module("app module") {
        bind<Context>() with provider { App.context }
        bind<RxBus>() with provider { RxBus.instance }
        bind<Constants>() with singleton { Constants(this.instance()) }
        bind<Repository>() with provider { Repository() }
        bind<RemoteDataSource>() with provider { RemoteDataSource() }

    }
}
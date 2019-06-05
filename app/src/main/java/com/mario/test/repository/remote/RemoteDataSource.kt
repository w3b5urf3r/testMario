package com.mario.test.repository.remote

import com.mario.test.App
import com.mario.test.api.Api
import kotlinx.coroutines.Deferred
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class RemoteDataSource : KodeinAware {

    override val kodein by closestKodein(App.context)
    private val api: Api by instance(arg = Pair(TIMEOUT, true))

    companion object {
        const val TIMEOUT = 3000L
    }

    fun getDefaultNumbersAsync(): Deferred<List<Int>> = api.getDefaultNumbersAsync()

}



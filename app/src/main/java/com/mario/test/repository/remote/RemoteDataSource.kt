package com.mario.test.repository.remote

import com.mario.test.App
import com.mario.test.api.Api
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

/**
 * Created by mariolopez on 28/12/17.
 */
class RemoteDataSource: KodeinAware {

    override val kodein by closestKodein(App.context)
    private val api : Api by instance(arg = TIMEOUT)

    companion object {
        const val TIMEOUT = 3000L
    }

}



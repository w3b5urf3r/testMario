package com.mario.test.repository


import com.mario.test.App
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

/**
 * Created by mariolopez on 27/12/17.
 */
abstract class BaseRepository :
    ISample,
        KodeinAware {

    final override val kodein by closestKodein(App.context)
}

interface ISample{
//    suspend fun sample() : List<Nothing>
}
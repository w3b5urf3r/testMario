package com.mario.test.repository


import com.mario.test.App
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

abstract class BaseRepository :
        INumbers,
        KodeinAware {

    final override val kodein by closestKodein(App.context)
}

interface INumbers {
    suspend fun getDefaultNumbers(): List<Int>
    suspend fun addNumbers(vararg numbers: Int)
    suspend fun getNumbers(): List<Int>
}
package com.mario.test.api


import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface Api {

    @GET("store/test/android/prestored.json")
    fun getDefaultNumbersAsync(): Deferred<List<Int>>

}
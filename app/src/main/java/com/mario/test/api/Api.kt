package com.mario.test.api

/**
 * Created by mariolopez on 27/12/17.
 */

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

interface Api {
    @GET
    fun pingGoogle(@Url url: String): Observable<Unit>

}
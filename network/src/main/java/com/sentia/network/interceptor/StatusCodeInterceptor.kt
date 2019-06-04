package com.sentia.network.interceptor

import com.sentia.network.ApiException
import com.sentia.network.ApiException.ErrorType.NOT_CONNECTED
import com.sentia.network.auth.AuthManager
import com.sentia.network.model.ResponseAPI
import com.sentia.network.strategy.IConnected
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.HttpURLConnection.HTTP_OK
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

/**
 * Created by mariolopez on 16/1/18.
 */
open class StatusCodeInterceptor(private val authManager: AuthManager,
                                 private val shouldLogoutIfUnauthorized: Boolean = false,
                                 private val networkManager: IConnected) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        checkIfInternetIsAvailable()

        val request = chain.request()

        val response = chain.proceed(request)

        val statusCode = response.code()
        if (statusCode < HTTP_OK || statusCode > 299 || response.body() == null) {
            handleErrorResponse(response)
        }

        return response
    }

    open fun handleErrorResponse(response: Response?) {
        checkIfShouldLogout(response)
        response?.body()?.let {
            val json = it.string()
            val moshi = Moshi.Builder().build()
            //todo check error from the actual API
            val errorAPIType = Types.newParameterizedType(ResponseAPI::class.java)

            val adapter: JsonAdapter<ResponseAPI> = moshi.adapter(errorAPIType)
            val kennardsErrorAPI = adapter.fromJson(json)!!
            throw ApiException(kennardsErrorAPI.statusCode,
                    ApiException.ErrorType.API_ERROR,
                    kennardsErrorAPI.message.toString())
        }
    }

    private fun checkIfInternetIsAvailable() {
        if (!networkManager.isConnected()) {
            throw ApiException(errorType = NOT_CONNECTED)
        }
    }

    private fun checkIfShouldLogout(response: Response?) {
        if (shouldLogoutIfUnauthorized && response?.code() == HTTP_UNAUTHORIZED && authManager.isUserLogged()) authManager.logout()
    }
}
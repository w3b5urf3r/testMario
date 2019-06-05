package com.mario.network.auth

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val authManager: AuthManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val authToken = authManager.getAuthToken()
        val requestBuilder = request.newBuilder()

        if (authToken.isNotEmpty()) {
            requestBuilder.addHeader(AuthKeys.AUTHORIZATION, "${Constants.BEARER} $authToken")
        }
        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }
    interface AuthKeys {
        companion object {
            const val AUTHORIZATION = "Authorization"
        }
    }

    interface Constants {
        companion object {
            const val BEARER = "Bearer"
        }
    }
}
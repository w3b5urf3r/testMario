package com.mario.network

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mario.network.auth.AuthInterceptor
import com.mario.network.auth.AuthManager
import com.mario.network.interceptor.StatusCodeInterceptor
import com.mario.network.strategy.IConnected
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit


object Network {
    lateinit var context: WeakReference<Context>
    private var debug: Boolean = false
    lateinit var authManager: AuthManager
    private const val AUTH_PREFERENCES = "AUTH PREFS"

    fun init(context: Context) {
        this.context = WeakReference(context)
        this.authManager = AuthManager(Network.context.get()?.getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE)!!, RxAuthBus.instance)
    }

    private fun getAuthInterceptor() = AuthInterceptor(authManager)

    fun getOkHttpClient(timeout: Long,
                        authManager: AuthManager,
                        networkStatusManager: IConnected,
                        shouldLogoutIfUnauthorized: Boolean): OkHttpClient =
            OkHttpClient.Builder()
                    .addInterceptor(getAuthInterceptor())
                    .addInterceptor(StatusCodeInterceptor(
                            shouldLogoutIfUnauthorized = shouldLogoutIfUnauthorized,
                            authManager = authManager,
                            networkManager = networkStatusManager))
                    .readTimeout(timeout, TimeUnit.SECONDS)
                    .connectTimeout(timeout, TimeUnit.SECONDS)
                    .writeTimeout(timeout, TimeUnit.SECONDS)
                    .addNetworkInterceptor(HttpLoggingInterceptor()
                            .setLevel(if (Network.debug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
                    .build()

    inline fun <reified T> getRetrofitAdapter(BASE_URL: String,
                                              timeout: Long,
                                              networkStatusManager: IConnected,
                                              shouldLogoutIfUnauthorized: Boolean): T =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(getMoshiConverterFactory())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(
                getOkHttpClient(
                    timeout,
                    authManager,
                    networkStatusManager,
                    shouldLogoutIfUnauthorized
                )
            )
            .build()
            .create(T::class.java)

    fun getMoshiConverterFactory(): MoshiConverterFactory =
            MoshiConverterFactory.create(Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build())
}
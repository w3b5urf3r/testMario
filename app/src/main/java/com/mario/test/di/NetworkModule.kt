package com.mario.test.di

import com.mario.network.Network
import com.mario.network.auth.AuthManager
import com.mario.network.strategy.IConnected
import com.mario.test.App
import com.mario.test.api.Api
import com.mario.test.util.Constants
import com.mario.test.util.connectivity.NetworkStatusManager
import org.kodein.di.Kodein
import org.kodein.di.generic.*

/**
 * 2 ways to retrieve classes defined by androidXModule inside a non-Android class(e.g. Activity, Fragment),
 * for example, ConnectivityManager:
 *
 * [1] using kodein with context to retrieve,
 *     override val kodein: Kodein by closestKodein(App.context)
 *     private val connectivityManager: ConnectivityManager by on(App.context).instance()
 *
 * [2] override kodeinContext,
 *     override val kodeinContext: KodeinContext<*> = kcontext(App.context)
 *     override val kodein: Kodein by closestKodein(App.context)
 *     private val connectivityManager: ConnectivityManager by instance()
 *
 *     NOTE: DO NOT call kodein.instance()
 *
 * see http://kodein.org/Kodein-DI/?6.0/android#_android_module
 */
val networkModule = Kodein.Module("network module") {
    bind<NetworkStatusManager>() with provider { NetworkStatusManager(on(App.context).instance()) }

    bind<Api>() with multiton { timeoutToShouldLogout: Pair<Long, Boolean> ->
        Network.getRetrofitAdapter<Api>(
                Constants.BASE_URL,
                timeoutToShouldLogout.first,
                instance<NetworkStatusManager>() as IConnected,
                timeoutToShouldLogout.second
        )
    }
    bind<AuthManager>() with provider { Network.authManager }
}
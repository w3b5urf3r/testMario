package com.mario.test.util.connectivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities
import android.os.Build
import com.mario.network.strategy.IConnected
import com.mario.test.App
import com.mario.test.util.primitives.orFalse
import io.reactivex.BackpressureStrategy
import io.reactivex.subjects.PublishSubject
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

/**
 *
 * Note: The implicit broadcast CONNECTIVITY_ACTION was deprecated in API level 28, but what will be deprecated is the
 * ability for a backgrounded App to receive network connection state changes.
 * we can still get notified of connectivity changes if the app component is instantiated (not destroyed)
 * and you have registered your receiver programmatically with its context, instead of doing it in the manifest.
 *
 * JobScheduler and WorkManager are provided as solutions for background App to migrate the need for these implicit broadcasts.
 *
 * TODO migrate to WorkManger to detect network connectivity change in background.
 *
 * Refer:
 * https://developer.android.com/reference/android/net/ConnectivityManager#CONNECTIVITY_ACTION
 * https://developer.android.com/topic/performance/background-optimization
 * https://stackoverflow.com/questions/36421930/connectivitymanager-connectivity-action-deprecated/36447866#36447866
 * https://stackoverflow.com/questions/50279364/android-workmanager-vs-jobscheduler
 */
class NetworkStatusManager(private val cm: ConnectivityManager) : BroadcastReceiver(), IConnected, KodeinAware {
    override val kodein: Kodein by closestKodein(App.context)

    private val publisher = PublishSubject.create<NetworkStatus>()
    val obs = publisher.toFlowable(BackpressureStrategy.LATEST).toObservable()!!

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            val status = getStatus()

            if (CONNECTIVITY_ACTION == intent?.action) {
                publisher.onNext(status)
            }
        }
    }

    /**
     * Get current network status.
     * activeNetworkInfo.getType() is deprecated in API level 28,
     * callers should switch to checking NetworkCapabilities#hasTransport
     * Refer:
     * https://developer.android.com/reference/android/net/NetworkInfo#getType()
     * https://stackoverflow.com/a/53078141/2722270
     */
    private fun getStatus(): NetworkStatus {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm.activeNetwork?.apply {
                val capabilities = cm.getNetworkCapabilities(this)
                return when {
                    capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI).orFalse() -> Wifi
                    capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR).orFalse() -> Mobile
                    else -> NotConnected
                }
            }
        } else {
            cm.activeNetworkInfo?.apply {
                return when (type) {
                    TYPE_WIFI -> Wifi
                    TYPE_MOBILE -> Mobile
                    else -> NotConnected
                }
            }
        }
        return NotConnected
    }

    override fun isConnected() = cm.activeNetworkInfo?.isConnected.orFalse()
}

sealed class NetworkStatus
object Wifi : NetworkStatus()
object Mobile : NetworkStatus()
object NotConnected : NetworkStatus()
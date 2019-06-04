package com.mario.test.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.mario.test.App
import com.mario.test.repository.BaseRepository
import com.mario.test.repository.Repository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import timber.log.Timber.e
import kotlin.coroutines.CoroutineContext

/**
 * Created by mariolopez on 27/12/17.
 */
open class BaseViewModel :
        ViewModel(),
        LifecycleObserver,
    RxViewModel,
        KodeinAware,
    ScopedViewModel,
    RepositoryViewModel {

    final override val kodein by closestKodein(App.context)

    override val repository: Repository by instance() //add other types of repository to the extending viewmodel
    override val compositeDisposable: CompositeDisposable = CompositeDisposable()
    override val obsDisposableMap: MutableMap<String, Disposable> = HashMap()
    override var viewModelJob: Job = Job()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun unSubscribeViewModel() {

        /**Disposable*/
        clearDisposable()

        /**Coroutines**/
        cancelJob()
    }

    override fun onCleared() {
        unSubscribeViewModel()
        super.onCleared()
    }
}

interface RepositoryViewModel {
    val repository: BaseRepository
}

interface RxViewModel {
    val compositeDisposable: CompositeDisposable
    val obsDisposableMap: MutableMap<String, Disposable>

    fun clearDisposable() {
        obsDisposableMap.forEach { it.value.dispose() }
        obsDisposableMap.clear()
        compositeDisposable.clear()
    }

    // We need this for continuous data streams (methods that subscribe to Observables, not Singles)
    // To ensure that there will always be just one subscribed per observable
    fun disposeOldConsumer(disposableKey: String) {
        if (obsDisposableMap.contains(disposableKey)) {
            obsDisposableMap[disposableKey]?.dispose()
            obsDisposableMap.remove(disposableKey)
        }
    }
}

interface ScopedViewModel : CoroutineScope {

    var viewModelJob: Job
    override val coroutineContext: CoroutineContext
        get() = viewModelJob + Dispatchers.Main

    fun cancelJob() = viewModelJob.cancel()

    suspend fun io(exceptionHandler: (Throwable.() -> Unit)? = { e(this, "error launching coroutines") },
                   block: suspend CoroutineScope.() -> Unit) =
            try {
                withContext(Dispatchers.IO) { block() }
            } catch (thr: Throwable) {
                exceptionHandler?.invoke(thr) ?: Unit
            }

    suspend fun ui(block: suspend CoroutineScope.() -> Unit) =
            withContext(Dispatchers.Main) { block() }
}
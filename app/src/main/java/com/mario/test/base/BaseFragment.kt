package com.mario.test.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment<T : BaseViewModel> : Fragment(), KodeinAware, CoroutineScope {

    override val kodein by closestKodein()
    lateinit var viewModel: T
    protected val compositeDisposable = CompositeDisposable()
    private lateinit var job: Job


    /**
     * start the loading via view Model and/or via arguments
     */
    abstract val starter: T.() -> Unit
    /**
     * render the UI based on ViewModel data
     */
    abstract val render: T.() -> Unit

    /**
     * init the UI
     */
    abstract fun initUi()

    abstract fun initViewModel(): T

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = initViewModel()
        initUi()
        starter.invoke(viewModel)
        render.invoke(viewModel)
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}
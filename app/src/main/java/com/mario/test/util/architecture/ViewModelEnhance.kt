package com.mario.test.util.architecture

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*


fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}


/**
 * bound with the LF of the activity
 */

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(): T = ViewModelProviders.of(this)[T::class.java]

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(vmFactory: ViewModelProvider.Factory): T = ViewModelProviders.of(this, vmFactory)[T::class.java]

/**
 * bound with the LF of the fragment
 */
inline fun <reified T : ViewModel> Fragment.getViewModel() = this.activity!!.getViewModel<T>()

inline fun <reified T : ViewModel> Fragment.getViewModel(factory: ViewModelProvider.Factory) = this.activity!!.getViewModel<T>(factory)

/**
 *  to be called just on OnCreate
 */

inline fun <reified T : ViewModel> Fragment.getViewModelForFragment(): T {
    return ViewModelProviders.of(this)[T::class.java]
}


inline fun <reified T : ViewModel> Fragment.withViewModelActivity(body: T.() -> Unit = {}): T {

    val vm = getViewModel<T>()
    vm.body()
    return vm
}

fun doNothing() {}

package com.mario.test.util.architecture

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*


fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}

fun <T : Any, L : LiveData<T>> Fragment.observeOnView(liveData: L, body: (T?) -> Unit) {
    liveData.observe(viewLifecycleOwner, Observer(body))
}

inline fun <reified T : ViewModel> FragmentActivity.withViewModel(
        crossinline factory: () -> T,
        body: T.() -> Unit
): T {
    val vm = getViewModel(factory)
    vm.body()
    return vm
}

/**
 * bound with the LF of the activity
 */

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(): T = ViewModelProviders.of(this)[T::class.java]

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(vmFactory: ViewModelProvider.Factory): T = ViewModelProviders.of(this, vmFactory)[T::class.java]

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> FragmentActivity.getViewModel(crossinline factory: () -> T): T {
    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }

    return ViewModelProviders.of(this, vmFactory)[T::class.java]
}

/**
 * bound with the LF of the fragment
 */
inline fun <reified T : ViewModel> Fragment.getViewModel() = this.activity!!.getViewModel<T>()

inline fun <reified T : ViewModel> Fragment.getViewModel(factory: ViewModelProvider.Factory) = this.activity!!.getViewModel<T>(factory)

inline fun <reified T : ViewModel> Fragment.getViewModel(crossinline factory: () -> T): T {
    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }

    return ViewModelProviders.of(this.activity!!, vmFactory)[T::class.java]
}


/**
 *  to be called just on OnCreate
 */

inline fun <reified T : ViewModel> Fragment.getViewModelForFragment(): T {
    return ViewModelProviders.of(this)[T::class.java]
}

inline fun <reified T : ViewModel> Fragment.getViewModelForFragment(crossinline factory: () -> T): T {

    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }

    return ViewModelProviders.of(this, vmFactory)[T::class.java]
}


/* Commodity extensions for Fragment Activity */
inline fun <reified T : ViewModel> FragmentActivity.withViewModel(body: T.() -> Unit): T {
    val vm = getViewModel<T>()
    vm.body()
    return vm
}

inline fun <reified T : ViewModel> Fragment.withViewModelActivity(body: T.() -> Unit = {}): T {

    val vm = getViewModel<T>()
    vm.body()
    return vm
}

/* Commodity extensions for Fragment */
inline fun <reified T : ViewModel> Fragment.withViewModelFragment(body: T.() -> Unit = {}): T {
    val vm = getViewModel<T>()
    vm.body()
    return vm
}

inline fun <reified T : ViewModel> Fragment.withViewModelFactoryFragment(factory: ViewModelProvider.Factory, body: T.() -> Unit = {}): T {
    val vm = getViewModel<T>(factory)
    vm.body()
    return vm
}

/**
 * @return return ViewModel bound with Fragment lifecycle
 */
inline fun <reified T : ViewModel> Fragment.withViewModel(body: T.() -> Unit = {}): T {
    val vm = getViewModelForFragment<T>()
    vm.body()
    return vm
}

fun doNothing() {}

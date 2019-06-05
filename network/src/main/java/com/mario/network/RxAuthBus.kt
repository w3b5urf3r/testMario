package com.mario.network

import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlin.reflect.KClass

class RxAuthBus {

    private val subject = PublishSubject.create<Any>()
    private val obs = subject.toFlowable(BackpressureStrategy.LATEST).toObservable()

    fun <E : Any> post(event: E) {
        subject.onNext(event)
    }

    fun <E : Any> observe(eventClass: KClass<E>): Observable<E> {
        return obs.ofType(eventClass.java)
    }

    private object Holder {
        val INSTANCE = RxAuthBus()
    }

    companion object Create {
        val instance: RxAuthBus by lazy { Holder.INSTANCE }
    }
}
package com.mario.test.util.architecture

import com.mario.test.util.architecture.Resource.Status.*


class Resource<out T> private constructor(val status: Status, val data: T?) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(SUCCESS, data)
        }

        fun <T> error(exception: AppException?): Resource<T> {
            return Resource(ERROR, null)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(LOADING, data)
        }
    }
}
package com.sentia.network.model

interface Response {
    companion object {
        const val STATUS_CODE = "statusCode"
        const val SUCCESS = "success"
        const val MESSAGE = "message"
        const val DATA = "data"
    }
}
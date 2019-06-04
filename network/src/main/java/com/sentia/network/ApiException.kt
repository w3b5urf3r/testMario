package com.sentia.network

import java.io.IOException

data class ApiException(val statusCode: Int = 0,
                        val errorType: ErrorType,
                        val errorMessage: String = "") : IOException() {
    enum class ErrorType {
        NOT_CONNECTED, API_ERROR
    }
}

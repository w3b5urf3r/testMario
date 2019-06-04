package com.sentia.network.model

import com.sentia.network.model.Response.Companion.MESSAGE
import com.sentia.network.model.Response.Companion.STATUS_CODE
import com.sentia.network.model.Response.Companion.SUCCESS
import com.squareup.moshi.Json

data class ResponseAPI(

        @Json(name = STATUS_CODE)
        val statusCode: Int,
        @Json(name = SUCCESS)
        val success: Boolean?,
        @Json(name = MESSAGE)
        val message: String?)
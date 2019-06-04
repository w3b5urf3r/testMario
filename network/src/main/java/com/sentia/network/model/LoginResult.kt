package com.sentia.network.model

import com.squareup.moshi.Json

/**
 * Created by mariolopez on 16/1/18.
 */
data class LoginResult(@Json(name = "access_token") val authToken: String)
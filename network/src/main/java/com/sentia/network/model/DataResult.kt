package com.sentia.network.model

import com.squareup.moshi.Json

/**
 * Created by mariolopez on 27/12/17.
 */
data class DataResult(@Json(name = "data") val data : SampleModels)
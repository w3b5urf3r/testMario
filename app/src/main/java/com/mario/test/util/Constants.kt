package com.mario.test.util

import android.content.res.Resources
import com.mario.test.R

/**
 * Global constants for the framework
 */

class Constants(private val res: Resources) {
    companion object {
        lateinit var BASE_URL: String
    }

    fun init() {
        BASE_URL = res.getString(R.string.baseUrl)
    }

}
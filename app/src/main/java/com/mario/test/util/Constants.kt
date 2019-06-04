package com.mario.test.util

import android.content.res.Resources
import com.mario.test.R

/**
 * Global constants for the framework
 */
const val PASSWORD_MIN_LENGTH: Int = 6
const val SP_AUTH: String = "Auth shared preferences"
const val CLICK_THROTTLE_DELAY = 500L
const val CLICK_THROTTLE_DELAY_LONG = 2000L

class Constants(private val res: Resources) {
    companion object {
        lateinit var BASE_URL: String
    }

    fun init() {
        BASE_URL = res.getString(R.string.baseUrl)
    }

    interface Keys {
        interface User {
            companion object {
                const val EMAIL = "email"
                const val PASSWORD = "password"
            }
        }

    }
}
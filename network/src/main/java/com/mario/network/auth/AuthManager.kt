package com.mario.network.auth

import android.content.SharedPreferences
import com.mario.network.RxAuthBus

class AuthManager(private val sp: SharedPreferences, private val rxAuthBus: RxAuthBus) {

    companion object {
        const val SP_KEY_AUTH_TOKEN = "Auth token"
        const val SP_FIREBASE_TOKEN = "Firebase Token"
    }

    fun getAuthToken() = sp.getString(SP_KEY_AUTH_TOKEN, "")!!

    fun isUserLogged(): Boolean = getAuthToken().isNotEmpty()

    fun logout() {
        sp.edit().clear().apply()
        rxAuthBus.post(NotAuthorised())
    }

    fun login(authToken: String) {
        sp.edit().putString(SP_KEY_AUTH_TOKEN, authToken).apply()
    }

    fun saveFireBaseToken(token: String) {
        sp.edit().putString(SP_FIREBASE_TOKEN, token).apply()
    }

    fun getFirebaseToken(): String = sp.getString(SP_FIREBASE_TOKEN, "")!!

}

sealed class AuthPass(val authorised: Boolean)

data class NotAuthorised(val auth: Boolean = false) : AuthPass(authorised = auth)
package io.github.gubarsergey.cam4learn.utility.helper

import android.content.Context
import org.jetbrains.anko.defaultSharedPreferences
import timber.log.Timber

private const val IS_USER_LOGGED_IN_KEY = "IS_USER_LOGGED_IN_KEY"
private const val TOKEN_KEY = "TOKEN_KEY"

class SharedPrefHelper(private val context: Context) {

    fun isUserLoggedIn(): Boolean =
        context.defaultSharedPreferences.getBoolean(IS_USER_LOGGED_IN_KEY, false).also {
            Timber.d("isUserLoggedIn: result = [$it]")
        }

    fun setUserLoggedIn(isLogged: Boolean) =
        context.defaultSharedPreferences.edit().putBoolean(IS_USER_LOGGED_IN_KEY, isLogged).apply().also {
            Timber.d("setUserLoggedIn")
        }

    fun getToken() = context.defaultSharedPreferences.getString(TOKEN_KEY, "").also {
        Timber.d("getToken: result = [$it]")
    }

    fun saveToken(token: String) =
        context.defaultSharedPreferences.edit().putString(TOKEN_KEY, token).apply().also {
            Timber.d("saveToken: token = [$token]")
        }
}
package io.github.gubarsergey.cam4learn.utility.helper

import android.content.Context
import org.jetbrains.anko.defaultSharedPreferences
import timber.log.Timber

private const val IS_USER_LOGGED_IN_KEY = "IS_USER_LOGGED_IN_KEY"
private const val TOKEN_KEY = "TOKEN_KEY"
private const val LOGIN_KEY = "LOGIN_KEY"
private const val PASSWORD_KEY = "PASSWORD_KEY"

class SharedPrefHelper(private val context: Context) {

    fun isUserLoggedIn(): Boolean =
        getBoolean(IS_USER_LOGGED_IN_KEY).also { Timber.d("isUserLoggedIn: result = [$it]") }

    fun setUserLoggedIn(isLogged: Boolean) =
        putBoolean(IS_USER_LOGGED_IN_KEY, isLogged).also { Timber.d("setUserLoggedIn") }

    fun getToken() = getString(TOKEN_KEY).also { Timber.d("getToken: result = [$it]") }

    fun saveToken(token: String) =
        putString(TOKEN_KEY, token).also { Timber.d("saveToken: token = [$token]") }

    fun getLogin() = getString(LOGIN_KEY).also { Timber.d("getToken: result = [$it]") }

    fun saveLogin(login: String) =
        putString(LOGIN_KEY, login).also { Timber.d("saveLogin: login = [$login]") }

    fun getPassword() = getString(PASSWORD_KEY).also { Timber.d("getPassword: result = [$it]") }

    fun savePassword(password: String) =
        putString(PASSWORD_KEY, password).also { Timber.d("savePassword: password = [$password]") }

    private fun getString(key: String) = context.defaultSharedPreferences.getString(key, "")
    private fun getBoolean(key: String) = context.defaultSharedPreferences.getBoolean(key, false)

    private fun putBoolean(key: String, value: Boolean) =
        context.defaultSharedPreferences.edit().putBoolean(key, value).apply()

    private fun putString(key: String, value: String) =
        context.defaultSharedPreferences.edit().putString(key, value).apply()

}
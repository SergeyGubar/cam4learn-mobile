package io.github.gubarsergey.cam4learn.utility.helper

import android.content.Context
import org.jetbrains.anko.defaultSharedPreferences
import timber.log.Timber

object SharedPrefHelper {

    private const val IS_USER_LOGGED_IN_KEY = "IS_USER_LOGGED_IN_KEY"

    fun isUserLoggedIn(context: Context): Boolean =
        context.defaultSharedPreferences.getBoolean(IS_USER_LOGGED_IN_KEY, false).also {
            Timber.d("isUserLoggedIn: result = [$it]")
        }

    fun setUserLoggedIn(context: Context, isLogged: Boolean) =
        context.defaultSharedPreferences.edit().putBoolean(IS_USER_LOGGED_IN_KEY, isLogged).apply().also {
            Timber.d("setUserLoggedIn")
        }
}
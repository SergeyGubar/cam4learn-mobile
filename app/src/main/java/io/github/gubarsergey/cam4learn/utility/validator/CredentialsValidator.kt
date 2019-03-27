package io.github.gubarsergey.cam4learn.utility.validator

import timber.log.Timber

object CredentialsValidator {

    fun isEmailValid(email: String): Boolean =
        (email.length > 5).also { Timber.d("isEmailValid: email = [$email], result = [$it]") }

    fun isPasswordValid(password: String): Boolean =
        password.length > 5.also { Timber.d("isPasswordValid: password = [$password], result = [$it]") }

}
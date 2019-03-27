package io.github.gubarsergey.cam4learn.utility.validator

object CredentialsValidator {
    fun isEmailValid(email: String): Boolean {
        return email.length > 5
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
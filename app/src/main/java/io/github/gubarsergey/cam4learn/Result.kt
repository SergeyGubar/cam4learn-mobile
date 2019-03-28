package io.github.gubarsergey.cam4learn

sealed class Result<T> {
    data class Success<T>(val result: T): Result<T>()
    data class Error<T>(val errorCode: String): Result<T>()
}
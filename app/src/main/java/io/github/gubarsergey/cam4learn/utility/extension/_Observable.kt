package io.github.gubarsergey.cam4learn.utility.extension

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.lang.IllegalStateException

fun <T> Observable<T>.workOnBackground(): Observable<T> {
    return this.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
}

fun <T> Single<T>.workOnBackground(): Single<T> {
    return this.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
}

fun <T> Single<Response<T>>.mapToResult(): Single<Result<T>> {
    return this.map { response ->
        if (response.isSuccessful) Result.success(response.body()!!) else Result.failure(
            IllegalStateException(
                response.code().toString().plus(" ---> ").plus(response.message())
            )
        )
    }
}
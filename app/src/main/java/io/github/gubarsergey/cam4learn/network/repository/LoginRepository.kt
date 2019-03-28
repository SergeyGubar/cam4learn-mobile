package io.github.gubarsergey.cam4learn.network.repository

import io.github.gubarsergey.cam4learn.Result
import io.github.gubarsergey.cam4learn.network.api.LoginApi
import io.github.gubarsergey.cam4learn.network.model.request.LoginRequestModel
import io.github.gubarsergey.cam4learn.network.model.response.LoginResponseModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class LoginRepository(private val api: LoginApi) {
    fun login(loginRequestModel: LoginRequestModel): Observable<Result<out LoginResponseModel?>> {
        val login = loginRequestModel.login
        val password = loginRequestModel.password
        Timber.d("login: login = [$login], password = [$password]")
        return api.login(LoginRequestModel(login, password)).map { response ->
            if (response.isSuccessful) Result.Success(response.body()) else Result.Error<LoginResponseModel>(response.code().toString())
        }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }
}
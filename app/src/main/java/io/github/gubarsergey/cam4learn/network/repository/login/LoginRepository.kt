package io.github.gubarsergey.cam4learn.network.repository.login

import io.github.gubarsergey.cam4learn.Result
import io.github.gubarsergey.cam4learn.network.api.LoginApi
import io.github.gubarsergey.cam4learn.network.entity.request.LoginRequestModel
import io.github.gubarsergey.cam4learn.network.entity.response.LoginResponseModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class LoginRepository(private val api: LoginApi) {

    fun login(loginRequestModel: LoginRequestModel): Single<Result<LoginResponseModel?>> {
        Timber.d("login: login = [${loginRequestModel.login}], password = [${loginRequestModel.password}]")
        return api.login(loginRequestModel).map { response ->
            if (response.isSuccessful) {
                Result.Success(response.body())
            } else Result.Error<LoginResponseModel?>(response.code().toString())
        }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }

    fun loginAsAdmin(loginRequestModel: LoginRequestModel): Single<Result<LoginResponseModel?>> {
        Timber.d("loginAsAdmin: login = [${loginRequestModel.login}], password = [${loginRequestModel.password}]")
        return api.loginAsAdmin(loginRequestModel).map { response ->
            if (response.isSuccessful) {
                Result.Success(response.body())
            } else Result.Error<LoginResponseModel?>(response.code().toString())
        }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }

}
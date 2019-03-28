package io.github.gubarsergey.cam4learn.network.api

import io.github.gubarsergey.cam4learn.Result
import io.github.gubarsergey.cam4learn.network.model.request.LoginRequestModel
import io.github.gubarsergey.cam4learn.network.model.response.LoginResponseModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.POST

interface LoginApi {
    @POST("/login")
    fun login(loginRequestModel: LoginRequestModel): Single<Response<LoginResponseModel>>
}
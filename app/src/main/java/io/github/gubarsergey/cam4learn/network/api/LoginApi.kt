package io.github.gubarsergey.cam4learn.network.api

import io.github.gubarsergey.cam4learn.network.entity.request.LoginRequestModel
import io.github.gubarsergey.cam4learn.network.entity.response.LoginResponseModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("login")
    fun login(@Body loginRequestModel: LoginRequestModel): Single<Response<LoginResponseModel>>

    @POST("loginAsAdmin")
    fun loginAsAdmin(@Body loginRequestModel: LoginRequestModel): Single<Response<LoginResponseModel>>
}
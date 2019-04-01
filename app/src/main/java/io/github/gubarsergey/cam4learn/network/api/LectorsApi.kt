package io.github.gubarsergey.cam4learn.network.api

import io.github.gubarsergey.cam4learn.network.entity.response.LectorResponseModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface LectorsApi {
    @GET("api/lectors")
    fun getLectors(@Header("JWT") token: String): Single<Response<List<LectorResponseModel>?>>
}
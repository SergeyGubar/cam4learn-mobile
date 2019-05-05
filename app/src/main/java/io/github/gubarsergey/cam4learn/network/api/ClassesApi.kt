package io.github.gubarsergey.cam4learn.network.api

import io.github.gubarsergey.cam4learn.network.entity.response.ClassResponseModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ClassesApi {
    @GET("api/lecture")
    fun getClasses(@Header("JWT") token: String): Single<Response<List<ClassResponseModel>>>
}

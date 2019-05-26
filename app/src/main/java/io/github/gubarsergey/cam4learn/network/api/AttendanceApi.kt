package io.github.gubarsergey.cam4learn.network.api

import io.github.gubarsergey.cam4learn.network.entity.response.AttendanceResponseModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface AttendanceApi {
    @GET("api/attendance")
    fun getClasses(@Header("JWT") token: String): Single<Response<List<AttendanceResponseModel>>>
}

package io.github.gubarsergey.cam4learn.network.api

import io.github.gubarsergey.cam4learn.network.entity.response.SubjectStatisticResponseModel
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SubjectStatisticApi {
    @GET("/api/subjectStatisticJson")
    fun getSubjectStatistic(
        @Header("JWT") token: String?,
        @Query("subjectId") id: Int
    ): Single<Response<List<SubjectStatisticResponseModel>?>>

    @GET("/api/subjectStatisticJson")
    fun getSubjectStatisticJson(
        @Header("JWT") token: String?,
        @Query("subjectId") id: Int
    ): Single<ResponseBody>

    @GET("/api/subjectStatisticCsv")
    fun getSubjectStatisticCsv(
        @Header("JWT") token: String?,
        @Query("subjectId") id: Int
    ): Single<ResponseBody>
}
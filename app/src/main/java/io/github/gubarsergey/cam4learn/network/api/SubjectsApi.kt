package io.github.gubarsergey.cam4learn.network.api

import io.github.gubarsergey.cam4learn.network.entity.response.SubjectResponseModel
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface SubjectsApi {
    @GET("api/getSubjects")
    fun getAllSubjects(@Header("JWT") token: String?): Single<Response<List<SubjectResponseModel>>>

    @GET("api/getSubjects")
    fun getAllSubjectsJson(@Header("JWT") token: String?): Single<ResponseBody>

    @GET("/api/getSubjectsCsv")
    fun getAllSubjectsCsv(@Header("JWT") token: String?): Single<ResponseBody>

}
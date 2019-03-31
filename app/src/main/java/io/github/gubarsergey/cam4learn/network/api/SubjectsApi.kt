package io.github.gubarsergey.cam4learn.network.api

import io.github.gubarsergey.cam4learn.network.entity.response.SubjectResponseModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface SubjectsApi {
    @GET("api/getSubjects")
    fun getAllSubjects(@Header("JWT") token: String?): Single<Response<List<SubjectResponseModel>>>
}
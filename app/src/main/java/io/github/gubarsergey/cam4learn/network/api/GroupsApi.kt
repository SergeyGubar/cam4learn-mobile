package io.github.gubarsergey.cam4learn.network.api

import io.github.gubarsergey.cam4learn.network.entity.response.GroupResponseModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface GroupsApi {
    @GET("api/group")
    fun getGroups(@Header("JWT") token: String): Single<Response<List<GroupResponseModel>>>
}
package io.github.gubarsergey.cam4learn.network.api

import io.github.gubarsergey.cam4learn.network.entity.response.FreeRoomResponseModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RoomsApi {
    @GET("api/freeRooms")
    fun getClasses(@Header("JWT") token: String,
                   @Query("classNum") classNum: Int,
                   @Query("date") date: String): Single<Response<List<FreeRoomResponseModel>>>
}
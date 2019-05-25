package io.github.gubarsergey.cam4learn.network.api

import io.github.gubarsergey.cam4learn.network.entity.request.AddClassRequestModel
import io.github.gubarsergey.cam4learn.network.entity.response.AddClassResponseModel
import io.github.gubarsergey.cam4learn.network.entity.response.ClassResponseModel
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ClassesApi {
    @GET("api/lecture")
    fun getClasses(@Header("JWT") token: String): Single<Response<List<ClassResponseModel>>>

    @POST("api/lecture")
    fun addClass(
        @Header("JWT") token: String,
        @Body requestModel: AddClassRequestModel
    ): Single<Response<AddClassResponseModel>>

    @DELETE("api/deleteLecture")
    fun deleteClass(
        @Header("JWT") token: String,
        @Query("id") id: Int
    ): Single<ResponseBody>

}

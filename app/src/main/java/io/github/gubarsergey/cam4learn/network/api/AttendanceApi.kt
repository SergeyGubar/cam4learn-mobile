package io.github.gubarsergey.cam4learn.network.api

import io.github.gubarsergey.cam4learn.network.entity.request.PutMarkRequestModel
import io.github.gubarsergey.cam4learn.network.entity.request.PutPresentRequestModel
import io.github.gubarsergey.cam4learn.network.entity.request.RecognizeAuditoryRequestModel
import io.github.gubarsergey.cam4learn.network.entity.response.AttendanceResponseModel
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface AttendanceApi {
    @GET("api/getStudents")
    fun getClasses(@Header("JWT") token: String, @Query("lecture") lectureId: Int): Single<Response<List<AttendanceResponseModel>>>

    @PUT("api/putMark")
    fun putMark(@Header("JWT") token: String, @Body markRequestModel: PutMarkRequestModel): Single<ResponseBody>

    @PUT("api/putPresent")
    fun putPresent(@Header("JWT") token: String, @Body markRequestModel: PutPresentRequestModel): Single<ResponseBody>

    @POST("api//api/recognizeAuditory")
    fun recognizeAuditory(@Header("JWT") token: String, @Body markRequestModel: RecognizeAuditoryRequestModel): Single<ResponseBody>
}

package io.github.gubarsergey.cam4learn.network.repository.attendance

import io.github.gubarsergey.cam4learn.network.api.AttendanceApi
import io.github.gubarsergey.cam4learn.network.entity.request.PutMarkRequestModel
import io.github.gubarsergey.cam4learn.network.entity.request.PutPresentRequestModel
import io.github.gubarsergey.cam4learn.network.entity.request.RecognizeAuditoryRequestModel
import io.github.gubarsergey.cam4learn.network.entity.response.AttendanceResponseModel
import io.github.gubarsergey.cam4learn.utility.extension.mapToResult
import io.github.gubarsergey.cam4learn.utility.extension.workOnBackground
import io.github.gubarsergey.cam4learn.utility.helper.SharedPrefHelper
import io.reactivex.Single
import okhttp3.ResponseBody

class AttendanceRepository(
    private val api: AttendanceApi,
    private val prefHelper: SharedPrefHelper
) {
    fun getAllAttendance(lectureId: Int): Single<Result<List<AttendanceResponseModel>>> {
        return api.getClasses(prefHelper.getToken(), lectureId).mapToResult().workOnBackground()
    }

    fun putMark(lectureId: Int, studentId: Int, mark: Int): Single<ResponseBody> {
        val requestModel = PutMarkRequestModel(lectureId, studentId, mark)
        return api.putMark(prefHelper.getToken(), requestModel).workOnBackground()
    }

    fun putPresent(lectureId: Int, studentId: Int): Single<ResponseBody> {
        val requestModel = PutPresentRequestModel(lectureId, studentId)
        return api.putPresent(prefHelper.getToken(), requestModel)
    }

    fun recognizeAuditory(lectureId: Int, photo: String): Single<ResponseBody> {
        val requestModel = RecognizeAuditoryRequestModel(lectureId, photo)
        return api.recognizeAuditory(prefHelper.getToken(), requestModel).workOnBackground()
    }
}
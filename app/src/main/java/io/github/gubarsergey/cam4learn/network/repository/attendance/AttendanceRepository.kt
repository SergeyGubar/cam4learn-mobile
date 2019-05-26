package io.github.gubarsergey.cam4learn.network.repository.attendance

import io.github.gubarsergey.cam4learn.network.api.AttendanceApi
import io.github.gubarsergey.cam4learn.network.entity.request.PutMarkRequestModel
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
}
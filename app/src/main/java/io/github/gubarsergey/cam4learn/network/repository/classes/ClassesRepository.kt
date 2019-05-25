package io.github.gubarsergey.cam4learn.network.repository.classes

import io.github.gubarsergey.cam4learn.network.api.ClassesApi
import io.github.gubarsergey.cam4learn.network.entity.request.AddClassRequestModel
import io.github.gubarsergey.cam4learn.network.entity.response.AddClassResponseModel
import io.github.gubarsergey.cam4learn.network.entity.response.ClassResponseModel
import io.github.gubarsergey.cam4learn.utility.extension.mapToResult
import io.github.gubarsergey.cam4learn.utility.extension.workOnBackground
import io.github.gubarsergey.cam4learn.utility.helper.SharedPrefHelper
import io.reactivex.Single
import okhttp3.ResponseBody
import timber.log.Timber

class ClassesRepository(
    private val api: ClassesApi,
    private val prefHelper: SharedPrefHelper
) {
    fun getAllClasses(): Single<Result<List<ClassResponseModel>>> {
        return api.getClasses(prefHelper.getToken())
            .mapToResult()
            .workOnBackground()
    }

    fun addClass(
        roomId: Int,
        groups: List<Int>,
        subjectId: Int,
        classNum: Int,
        type: String,
        date: String
    ): Single<Result<AddClassResponseModel>> {
        Timber.d("addClass: room = $roomId groups = $groups subjectId = $subjectId classNum = $classNum type = [$type] date = [$date]")
        val requestModel = AddClassRequestModel(
            roomId,
            groups,
            subjectId,
            classNum,
            type,
            date
        )
        return api.addClass(prefHelper.getToken(), requestModel)
            .mapToResult()
            .workOnBackground()
    }

    fun deleteClass(id: Int): Single<ResponseBody>{
        return api.deleteClass(prefHelper.getToken(), id)
            .workOnBackground()
    }
}
package io.github.gubarsergey.cam4learn.network.repository.classes

import io.github.gubarsergey.cam4learn.network.api.ClassesApi
import io.github.gubarsergey.cam4learn.network.entity.response.ClassResponseModel
import io.github.gubarsergey.cam4learn.utility.extension.mapToResult
import io.github.gubarsergey.cam4learn.utility.extension.workOnBackground
import io.github.gubarsergey.cam4learn.utility.helper.SharedPrefHelper
import io.reactivex.Single

class ClassesRepository(
    private val api: ClassesApi,
    private val prefHelper: SharedPrefHelper
) {
    fun getAllClasses(): Single<Result<List<ClassResponseModel>>> {
        return api.getClasses(prefHelper.getToken())
            .mapToResult()
            .workOnBackground()
    }
}
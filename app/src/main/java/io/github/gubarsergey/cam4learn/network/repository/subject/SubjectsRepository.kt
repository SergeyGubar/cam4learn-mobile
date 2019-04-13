package io.github.gubarsergey.cam4learn.network.repository.subject

import io.github.gubarsergey.cam4learn.Result
import io.github.gubarsergey.cam4learn.network.api.SubjectsApi
import io.github.gubarsergey.cam4learn.network.entity.response.SubjectResponseModel
import io.github.gubarsergey.cam4learn.utility.helper.SharedPrefHelper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

class SubjectsRepository(
    private val api: SubjectsApi,
    private val prefHelper: SharedPrefHelper
) {
    fun getAllSubjects(): Single<Result<List<SubjectResponseModel>?>> {
        return api.getAllSubjects(prefHelper.getToken()).map { response ->
            if (response.isSuccessful) {
                Result.Success(response.body())
            } else Result.Error<List<SubjectResponseModel>?>(response.code().toString())
        }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }

    fun getAllSubjectsJson(): Single<ResponseBody> {
        return api.getAllSubjectsJson(prefHelper.getToken())
            .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }

    fun getAllSubjectsCsv(): Single<ResponseBody> {
        return api.getAllSubjectsCsv(prefHelper.getToken())
            .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }

}
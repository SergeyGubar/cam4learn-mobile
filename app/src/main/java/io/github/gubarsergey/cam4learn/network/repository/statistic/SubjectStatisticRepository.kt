package io.github.gubarsergey.cam4learn.network.repository.statistic

import io.github.gubarsergey.cam4learn.Result
import io.github.gubarsergey.cam4learn.network.api.SubjectStatisticApi
import io.github.gubarsergey.cam4learn.network.entity.response.SubjectStatisticResponseModel
import io.github.gubarsergey.cam4learn.utility.helper.SharedPrefHelper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SubjectStatisticRepository(
    private val prefHelper: SharedPrefHelper,
    private val api: SubjectStatisticApi
) {
    fun getStatistic(id: Int): Single<Result<SubjectStatisticResponseModel?>> {
        return api.getSubjectStatistic(prefHelper.getToken(), id).map { response ->
            if (response.isSuccessful) {
                Result.Success(response.body())
            } else Result.Error<SubjectStatisticResponseModel?>(response.code().toString())
        }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }
}
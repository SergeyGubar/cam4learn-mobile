package io.github.gubarsergey.cam4learn.network.repository.statistic

import io.github.gubarsergey.cam4learn.Result
import io.github.gubarsergey.cam4learn.network.api.SubjectStatisticApi
import io.github.gubarsergey.cam4learn.network.entity.response.SubjectStatisticResponseModel
import io.github.gubarsergey.cam4learn.utility.helper.SharedPrefHelper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import timber.log.Timber

class SubjectStatisticRepository(
    private val prefHelper: SharedPrefHelper,
    private val api: SubjectStatisticApi
) {
    fun getStatistic(id: Int): Single<Result<List<SubjectStatisticResponseModel>?>> {
        Timber.d("getStatistic: id = [$id]")
        return api.getSubjectStatistic(prefHelper.getToken(), id).map { response ->
            if (response.isSuccessful) {
                Result.Success(response.body())
            } else Result.Error<List<SubjectStatisticResponseModel>?>(response.code().toString())
        }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }

    fun getStatisticJson(id: Int): Single<ResponseBody> {
        Timber.d("getStatisticJson: id = [$id]")
        return api.getSubjectStatisticJson(prefHelper.getToken(), id).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun getStatisticCsv(id: Int): Single<ResponseBody> {
        Timber.d("getStatisticCsv: id = [$id]")
        return api.getSubjectStatisticCsv(prefHelper.getToken(), id).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

}
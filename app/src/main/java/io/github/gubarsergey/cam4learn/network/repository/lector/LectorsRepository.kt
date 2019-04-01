package io.github.gubarsergey.cam4learn.network.repository.lector

import io.github.gubarsergey.cam4learn.Result
import io.github.gubarsergey.cam4learn.network.api.LectorsApi
import io.github.gubarsergey.cam4learn.network.entity.response.LectorResponseModel
import io.github.gubarsergey.cam4learn.utility.helper.SharedPrefHelper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class LectorsRepository(
    private val prefHelper: SharedPrefHelper,
    private val api: LectorsApi
) {
    fun getLectors(): Single<Result<List<LectorResponseModel>?>> {
        Timber.d("getLectors")
        return api.getLectors(prefHelper.getToken()).map { response ->
            if (response.isSuccessful) {
                Result.Success(response.body())
            } else Result.Error<List<LectorResponseModel>?>(response.code().toString())
        }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }
}
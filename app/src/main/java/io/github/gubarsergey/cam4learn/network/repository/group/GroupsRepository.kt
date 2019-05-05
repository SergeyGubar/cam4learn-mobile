package io.github.gubarsergey.cam4learn.network.repository.group

import io.github.gubarsergey.cam4learn.network.api.GroupsApi
import io.github.gubarsergey.cam4learn.network.entity.response.GroupResponseModel
import io.github.gubarsergey.cam4learn.utility.extension.mapToResult
import io.github.gubarsergey.cam4learn.utility.extension.workOnBackground
import io.github.gubarsergey.cam4learn.utility.helper.SharedPrefHelper
import io.reactivex.Single

class GroupsRepository(
    private val api: GroupsApi,
    private val prefHelper: SharedPrefHelper
) {
    fun getAllGroups(): Single<Result<List<GroupResponseModel>>> {
        return api.getGroups(prefHelper.getToken())
            .mapToResult()
            .workOnBackground()
    }
}
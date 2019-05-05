package io.github.gubarsergey.cam4learn.network.repository.room

import io.github.gubarsergey.cam4learn.network.api.RoomsApi
import io.github.gubarsergey.cam4learn.network.entity.response.FreeRoomResponseModel
import io.github.gubarsergey.cam4learn.utility.extension.mapToResult
import io.github.gubarsergey.cam4learn.utility.extension.workOnBackground
import io.github.gubarsergey.cam4learn.utility.helper.SharedPrefHelper
import io.reactivex.Single

class RoomRepository(
    private val api: RoomsApi,
    private val prefHelper: SharedPrefHelper
) {
    fun getFreeRooms(classNum: Int, date: String): Single<Result<List<FreeRoomResponseModel>>> {
        return api.getClasses(prefHelper.getToken(), classNum, date)
            .mapToResult()
            .workOnBackground()
    }
}
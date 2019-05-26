package io.github.gubarsergey.cam4learn.network.entity.request

data class PutMarkRequestModel(
    val lecture: Int,
    val id: Int,
    val mark: Int
)
package io.github.gubarsergey.cam4learn.network.entity.response

data class AttendanceResponseModel(
    val group: String,
    val isPresent: Boolean,
    val surname: String,
    val value: Int
)
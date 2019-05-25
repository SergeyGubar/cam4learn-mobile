package io.github.gubarsergey.cam4learn.network.entity.request

data class AddClassRequestModel(
    val roomId: Int,
    val groups: List<Int>,
    val subjectId: Int,
    val classNum: Int,
    val lectureType: String,
    val date: String
)
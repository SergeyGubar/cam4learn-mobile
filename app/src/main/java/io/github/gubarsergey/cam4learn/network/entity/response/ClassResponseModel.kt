package io.github.gubarsergey.cam4learn.network.entity.response

data class ClassResponseModel(
    val id: String,
    val classNum: Int,
    val date: String,
    val groups: List<String>,
    val lectureType: String,
    val room: String,
    val subject: String
)
package io.github.gubarsergey.cam4learn.network.entity.response

data class SubjectStatisticResponseModel(
    val attendanceList: List<AttendanceResponseModel>,
    val date: String,
    val subject: String
)
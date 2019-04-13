package io.github.gubarsergey.cam4learn.ui.subject.statistic

import io.github.gubarsergey.cam4learn.network.entity.response.SubjectStatisticResponseModel

class SubjectStatisticUIMapper {
    fun toUIModel(item: SubjectStatisticResponseModel): List<SubjectStatisticUIModel> {
        return item.attendanceList.map { attendanceResponseModel ->
            with(attendanceResponseModel) {
                SubjectStatisticUIModel(
                    "AAPZ",
                    surname,
                    item.date,
                    group,
                    isPresent.toString(),
                    value.toString()
                )
            }
        }
    }
}
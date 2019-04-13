package io.github.gubarsergey.cam4learn.ui.subject.statistic

import android.view.View
import android.view.ViewGroup
import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.ui.BaseAdapter
import io.github.gubarsergey.cam4learn.ui.BaseViewHolder
import io.github.gubarsergey.cam4learn.utility.extension.inflater
import kotlinx.android.synthetic.main.list_item_subject_statistic.*

class SubjectStatisticAdapter(items: MutableList<SubjectStatisticUIModel> = mutableListOf()) :
    BaseAdapter<SubjectStatisticUIModel>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<SubjectStatisticUIModel> =
        SubjectStatisticViewHolder(parent.context.inflater.inflate(R.layout.list_item_subject_statistic, parent, false))


    inner class SubjectStatisticViewHolder(view: View) : BaseViewHolder<SubjectStatisticUIModel>(view) {
        override fun bind(item: SubjectStatisticUIModel) {
            with(item) {
                subject_statistic_item_attendance.text = attendance
                subject_statistic_item_date.text = date
                subject_statistic_item_grade.text = grade
                subject_statistic_item_group_name.text = group
                subject_statistic_item_student_name.text = studentName
                subject_statistic_item_name.text = name
            }
        }
    }
}
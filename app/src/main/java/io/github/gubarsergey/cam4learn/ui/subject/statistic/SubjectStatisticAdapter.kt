package io.github.gubarsergey.cam4learn.ui.subject.statistic

import android.view.View
import android.view.ViewGroup
import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.network.entity.response.SubjectStatisticResponseModel
import io.github.gubarsergey.cam4learn.ui.BaseAdapter
import io.github.gubarsergey.cam4learn.ui.BaseViewHolder
import io.github.gubarsergey.cam4learn.utility.extension.inflater

class SubjectStatisticAdapter(items: MutableList<SubjectStatisticResponseModel> = mutableListOf()) :
    BaseAdapter<SubjectStatisticResponseModel>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<SubjectStatisticResponseModel> =
        SubjectStatisticViewHolder(parent.context.inflater.inflate(R.layout.list_item_subject_statistic, parent, false))


    inner class SubjectStatisticViewHolder(view: View) : BaseViewHolder<SubjectStatisticResponseModel>(view) {
        override fun bind(item: SubjectStatisticResponseModel) {

        }

    }
}
package io.github.gubarsergey.cam4learn.ui.subject

import android.view.View
import android.view.ViewGroup
import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.network.entity.response.SubjectResponseModel
import io.github.gubarsergey.cam4learn.ui.BaseAdapter
import io.github.gubarsergey.cam4learn.ui.BaseViewHolder
import io.github.gubarsergey.cam4learn.utility.extension.inflater
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_subject.*


class SubjectsAdapter(subjects: MutableList<SubjectResponseModel> = mutableListOf()) :
    BaseAdapter<SubjectResponseModel>(subjects) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<SubjectResponseModel> =
        SubjectViewHolder(parent.context.inflater.inflate(R.layout.list_item_subject, parent, false))

    inner class SubjectViewHolder(view: View) : BaseViewHolder<SubjectResponseModel>(view), LayoutContainer {
        override fun bind(item: SubjectResponseModel) {
            subject_item_id.text = item.id.toString()
            subject_item_title.text = item.title
        }
    }
}

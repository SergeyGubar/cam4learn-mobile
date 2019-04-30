package io.github.gubarsergey.cam4learn.ui.classes

import android.view.View
import android.view.ViewGroup
import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.network.entity.response.ClassResponseModel
import io.github.gubarsergey.cam4learn.ui.BaseAdapter
import io.github.gubarsergey.cam4learn.ui.BaseViewHolder
import io.github.gubarsergey.cam4learn.utility.extension.inflater

class ClassesAdapter(classes: MutableList<ClassResponseModel> = mutableListOf(),
                     private val onLongClick: (ClassResponseModel) -> Unit): BaseAdapter<ClassResponseModel>(classes) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ClassResponseModel> {
        return ClassViewHolder(parent.context.inflater.inflate(R.layout.item_class, parent, false))
    }

    inner class ClassViewHolder(view: View): BaseViewHolder<ClassResponseModel>(view) {
        override fun bind(item: ClassResponseModel) {
            containerView.setOnLongClickListener {
                onLongClick(item)
                false
            }
            // TODO
        }
    }
}
package io.github.gubarsergey.cam4learn.ui.classes

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.network.entity.response.ClassResponseModel
import io.github.gubarsergey.cam4learn.ui.BaseAdapter
import io.github.gubarsergey.cam4learn.ui.BaseViewHolder
import io.github.gubarsergey.cam4learn.utility.extension.inflater
import kotlinx.android.synthetic.main.item_class.*

class ClassesAdapter(
    classes: MutableList<ClassResponseModel> = mutableListOf(),
    private val onLongClick: (ClassResponseModel) -> Unit,
    private val onClick: (ClassResponseModel) -> Unit
) : BaseAdapter<ClassResponseModel>(classes) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ClassResponseModel> {
        return ClassViewHolder(parent.context.inflater.inflate(R.layout.item_class, parent, false))
    }

    fun removeItem(id: Int) {
        val index = items.indexOfFirst { it.id == id }
        items.removeAt(index)
        notifyItemRemoved(index)
    }

    inner class ClassViewHolder(view: View) : BaseViewHolder<ClassResponseModel>(view) {
        override fun bind(item: ClassResponseModel) {
            containerView.setOnLongClickListener {
                onLongClick(item)
                false
            }
            containerView.setOnClickListener {
                onClick(item)
            }
            with(item) {
                class_date.text = date
                class_room_number.text = room
                class_pair_number.text = classNum.toString()
                class_subject.text = subject
                class_name.text = lectureType
                if (groups.isNotEmpty()) {
                    class_groups.text = groups.reduce { acc, s -> acc.plus(" ").plus(s) }
                }
            }
        }
    }
}
package io.github.gubarsergey.cam4learn.ui.classes.add

import android.view.View
import android.view.ViewGroup
import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.network.entity.response.GroupResponseModel
import io.github.gubarsergey.cam4learn.ui.BaseAdapter
import io.github.gubarsergey.cam4learn.ui.BaseViewHolder
import io.github.gubarsergey.cam4learn.utility.extension.inflater
import kotlinx.android.synthetic.main.item_group.*
import kotlinx.android.synthetic.main.item_group.view.*

class GroupsAdapter(
    items: MutableList<GroupResponseModel> = mutableListOf(),
    private val checkedStates: MutableList<Boolean> = mutableListOf()
) : BaseAdapter<GroupResponseModel>(items) {

    fun updateCheckedStates(states: List<Boolean>) {
        this.checkedStates.clear()
        this.checkedStates.addAll(states)
        notifyDataSetChanged()
    }

    fun getCheckedItems(): List<GroupResponseModel> {
        return checkedStates.mapIndexedNotNull { index, isChecked ->
            if (isChecked) items[index] else null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<GroupResponseModel> {
        return GroupViewHolder(parent.context.inflater.inflate(R.layout.item_group, parent, false))
    }

    inner class GroupViewHolder(view: View) : BaseViewHolder<GroupResponseModel>(view) {
        override fun bind(item: GroupResponseModel) {
            item_group_name.text = item.name
            item_group_is_selected.isChecked = checkedStates[adapterPosition]
            item_group_is_selected.setOnCheckedChangeListener { buttonView, isChecked ->
                checkedStates[adapterPosition] = isChecked
            }
        }
    }
}
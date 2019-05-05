package io.github.gubarsergey.cam4learn.ui.classes.add

import android.view.View
import android.view.ViewGroup
import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.network.entity.response.GroupResponseModel
import io.github.gubarsergey.cam4learn.ui.BaseAdapter
import io.github.gubarsergey.cam4learn.ui.BaseViewHolder
import io.github.gubarsergey.cam4learn.utility.extension.inflater
import kotlinx.android.synthetic.main.item_group.view.*

class GroupsAdapter(items: MutableList<GroupResponseModel> = mutableListOf()) : BaseAdapter<GroupResponseModel>(items) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<GroupResponseModel> {
        return GroupViewHolder(parent.context.inflater.inflate(R.layout.item_group, parent, false))
    }

    inner class GroupViewHolder(view: View): BaseViewHolder<GroupResponseModel>(view) {
        override fun bind(item: GroupResponseModel) {
            containerView.item_group_name.text = item.name
            containerView.item_group_is_selected.setOnCheckedChangeListener { buttonView, isChecked ->
                // TODO
            }
        }
    }
}
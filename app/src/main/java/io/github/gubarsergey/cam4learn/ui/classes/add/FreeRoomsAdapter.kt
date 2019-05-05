package io.github.gubarsergey.cam4learn.ui.classes.add

import android.view.View
import android.view.ViewGroup
import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.network.entity.response.FreeRoomResponseModel
import io.github.gubarsergey.cam4learn.ui.BaseAdapter
import io.github.gubarsergey.cam4learn.ui.BaseViewHolder
import io.github.gubarsergey.cam4learn.utility.extension.inflater
import kotlinx.android.synthetic.main.item_free_room.*

class FreeRoomsAdapter(items: MutableList<FreeRoomResponseModel> = mutableListOf()) :
    BaseAdapter<FreeRoomResponseModel>(items) {

    private val checkedStates = mutableListOf<Boolean>()
    private var previouslyCheckedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): BaseViewHolder<FreeRoomResponseModel> {
        return FreeRoomViewHolder(parent.context.inflater.inflate(R.layout.item_free_room, parent, false))
    }

    fun updateCheckedStates(states: List<Boolean>) {
        this.checkedStates.clear()
        this.checkedStates.addAll(states)
        notifyDataSetChanged()
    }

    inner class FreeRoomViewHolder(view: View) : BaseViewHolder<FreeRoomResponseModel>(view) {
        override fun bind(item: FreeRoomResponseModel) {
            val position = adapterPosition
            item_room_number_text_view.text = item.room
            item_room_radiobutton.setOnCheckedChangeListener(null)
            item_room_radiobutton.isChecked = checkedStates[position]
            item_room_radiobutton.setOnCheckedChangeListener { buttonView, isChecked ->
                if (previouslyCheckedPosition != -1) {
                    checkedStates[previouslyCheckedPosition] = false
                    notifyItemChanged(previouslyCheckedPosition)
                }
                previouslyCheckedPosition = position
                checkedStates[position] = true
                notifyItemChanged(position)
            }
        }
    }
}
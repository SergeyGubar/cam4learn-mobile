package io.github.gubarsergey.cam4learn.ui.teacher

import android.view.View
import android.view.ViewGroup
import io.github.gubarsergey.cam4learn.R
import io.github.gubarsergey.cam4learn.network.entity.response.LectorResponseModel
import io.github.gubarsergey.cam4learn.ui.BaseAdapter
import io.github.gubarsergey.cam4learn.ui.BaseViewHolder
import io.github.gubarsergey.cam4learn.utility.extension.inflater
import kotlinx.android.synthetic.main.list_item_lector.*

class LectorsAdapter(lectors: MutableList<LectorResponseModel> = mutableListOf()) :
    BaseAdapter<LectorResponseModel>(lectors) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<LectorResponseModel> =
        LectorViewHolder(parent.context.inflater.inflate(R.layout.list_item_lector, parent, false))

    inner class LectorViewHolder(view: View) : BaseViewHolder<LectorResponseModel>(view) {
        override fun bind(item: LectorResponseModel) {
            lector_item_id.text = item.id.toString()
            lector_item_name.text = item.name
            lector_item_surname.text = item.surname
        }
    }
}
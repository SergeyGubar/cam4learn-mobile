package io.github.gubarsergey.cam4learn.ui

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder<T>(view: View): RecyclerView.ViewHolder(view) {
    abstract fun bind(item: T)
}
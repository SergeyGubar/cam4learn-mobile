package io.github.gubarsergey.cam4learn.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.LayoutContainer

abstract class BaseViewHolder<T>(view: View): RecyclerView.ViewHolder(view), LayoutContainer {

    override val containerView: View
        get() = itemView

    abstract fun bind(item: T)

}
package io.github.gubarsergey.cam4learn.ui

import android.support.v7.widget.RecyclerView

abstract class BaseAdapter<T>(protected val items: MutableList<T> = mutableListOf()) : RecyclerView.Adapter<BaseViewHolder<T>>() {
    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(items[position])
    }

    fun swapData(newData: List<T>) {
        items.clear()
        items.addAll(newData)
        notifyDataSetChanged()
    }
}
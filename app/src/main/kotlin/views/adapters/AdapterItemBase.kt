package com.michalfaber.drawertemplate.views.adapters

import android.support.v7.widget.RecyclerView
import kotlin.reflect.KClass

abstract class AdapterItemBase<T>(val viewHolderClass: KClass<T>): AdapterItem {
    override val viewType: Int = viewHolderClass.hashCode()

    abstract fun bindViewHolder(viewHolder: T)
    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder) {
        bindViewHolder(viewHolder as T)
    }
}
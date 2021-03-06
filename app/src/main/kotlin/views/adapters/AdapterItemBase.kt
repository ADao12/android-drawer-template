package com.michalfaber.drawertemplate.views.adapters

import android.support.v7.widget.RecyclerView
import kotlin.reflect.KClass

abstract class AdapterItemBase<T>(val viewHolderClass: KClass<T>) : AdapterItem {
    companion object {
        val ID_GENERATOR_MIN_VALUE: Long = 2 shl 5
        var nextId: Long = ID_GENERATOR_MIN_VALUE;
    }
    // if not overriden the id is generated
    override val id = ++nextId
    override val viewType: Int = viewHolderClass.hashCode()

    abstract fun bindViewHolder(viewHolder: T)

    [suppress("UNCHECKED_CAST")]
    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder) {
        bindViewHolder(viewHolder as T)
    }

}
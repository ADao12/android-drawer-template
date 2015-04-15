package com.michalfaber.drawertemplate.views.adapters

import android.support.v7.widget.RecyclerView

trait AdapterItem {
    val selectable: Boolean
    val id: Long
    val viewType: Int
    fun bindViewHolder(viewHolder: RecyclerView.ViewHolder)
}

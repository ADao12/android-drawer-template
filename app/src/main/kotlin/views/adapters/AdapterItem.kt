package com.michalfaber.drawertemplate.views.adapters

import android.support.v7.widget.RecyclerView

trait AdapterItem {
    val viewType: Int
    fun bindViewHolder(viewHolder: RecyclerView.ViewHolder)
}

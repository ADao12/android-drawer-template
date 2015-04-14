package com.michalfaber.drawertemplate.views.adapters.drawer

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.michalfaber.drawertemplate.R
import com.michalfaber.drawertemplate.views.adapters.AdapterItemBase

import java.util.ArrayList
import java.util.HashMap
import kotlin.properties.Delegates
import kotlin.reflect
import kotlin.reflect.KClass

class DrawerItemSimple(override val id: Long, val icon: Drawable, val label: String, val onClick: (Long) -> Unit) : AdapterItemBase<ViewHolderSimple>(ViewHolderSimple::class){
    override val isSelectable: Boolean = true

    override fun bindViewHolder(viewHolder: ViewHolderSimple) {
        viewHolder.icon.setImageDrawable(icon)
        viewHolder.label.setText(label)
        viewHolder.itemView.setOnClickListener { onClick(id) }
    }
}

class DrawerItemHeader(val label: String) : AdapterItemBase<ViewHolderSection>(ViewHolderSection::class){
    override val isSelectable: Boolean = false

    override fun bindViewHolder(viewHolder: ViewHolderSection) {
        viewHolder.label.setText(label)
    }
}

class DrawerItemSpinner(val subs: List<DrawerItemSpinner.DrawerItemSpinnerSubItem>, val currentItem: Int)  : AdapterItemBase<ViewHolderSpinnerItem>(ViewHolderSpinnerItem::class){
    private val subItems = subs.map { AdapterItemSpinnerSubItemInternal(it.id, this, it.icon, it.label, it.onClick) }
    private var expanded = false

    private fun makeCurrent(currentItem: Int): DrawerItemSpinner {
        return DrawerItemSpinner(subs, currentItem)
    }

    override val isSelectable: Boolean = false

    override fun bindViewHolder(viewHolder: ViewHolderSpinnerItem) {
        viewHolder.label.setText(subItems[currentItem].label)
        viewHolder.itemView.setOnClickListener( {

            val currPos = viewHolder.getAdapterPosition()
            if (expanded) {
                viewHolder.supervisor.removeItems(currPos + 1, subItems)

            } else {
                viewHolder.supervisor.addItems(currPos + 1, subItems)
            }

            expanded = !expanded
        })
    }

    private class AdapterItemSpinnerSubItemInternal(override val id: Long, val parentSpinner: DrawerItemSpinner, val icon: Drawable, val label: String, val onClick: (Long) -> Unit) : AdapterItemBase<ViewHolderSpinnerSubItem>(ViewHolderSpinnerSubItem::class){
        override val isSelectable: Boolean = false
        override fun bindViewHolder(viewHolder: ViewHolderSpinnerSubItem) {
            viewHolder.label.setText(label)
            viewHolder.itemView.setOnClickListener {
                val parentIdx = viewHolder.supervisor.indexOfItem(parentSpinner)
                val currPos = viewHolder.getAdapterPosition()
                val thisIndex = currPos - parentIdx - 1
                viewHolder.supervisor.swapItem(parentIdx, parentSpinner.makeCurrent(thisIndex))
                viewHolder.supervisor.removeItems(parentIdx + 1, parentSpinner.subItems)
                onClick(id)
            }
        }
    }

    public data class DrawerItemSpinnerSubItem(val id: Long, val icon: Drawable, val label: String, val onClick: (Long) -> Unit)
}

package com.michalfaber.drawertemplate.views.adapters.drawer

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.v4.content.res.ResourcesCompat
import com.michalfaber.drawertemplate.MainApplication
import com.michalfaber.drawertemplate.R
import com.michalfaber.drawertemplate.views.adapters.AdapterItemBase

class DrawerItemMedium(override val id: Long, val icon: Drawable, val label: String, val onClick: (Long) -> Unit, override val selectable: Boolean = true) : AdapterItemBase<ViewHolderMedium>(ViewHolderMedium::class) {
    override fun bindViewHolder(viewHolder: ViewHolderMedium) {
        viewHolder.icon.setImageDrawable(icon)
        viewHolder.label.setText(label)
        viewHolder.itemView.setOnClickListener { onClick(id) }
    }
}

class DrawerItemSmall(override val id: Long, val icon: Drawable, val label: String, val onClick: (Long) -> Unit, override val selectable: Boolean = true) : AdapterItemBase<ViewHolderSmall>(ViewHolderSmall::class) {
    override fun bindViewHolder(viewHolder: ViewHolderSmall) {
        viewHolder.icon.setImageDrawable(icon)
        viewHolder.label.setText(label)
        viewHolder.itemView.setOnClickListener { onClick(id) }
    }
}

class DrawerItemSeparator() : AdapterItemBase<ViewHolderSeparator>(ViewHolderSeparator::class) {
    override val selectable: Boolean = false

    override fun bindViewHolder(viewHolder: ViewHolderSeparator) {
    }
}

class DrawerItemHeader(val label: String) : AdapterItemBase<ViewHolderHeader>(ViewHolderHeader::class) {
    override val selectable: Boolean = false

    override fun bindViewHolder(viewHolder: ViewHolderHeader) {
        viewHolder.label.setText(label)
    }
}

class DrawerItemSpinner(val iconExpand: Drawable, val iconCollapse: Drawable, val subs: List<DrawerItemSpinner.Item>, val currentItem: Int = 0) : AdapterItemBase<ViewHolderSpinner>(ViewHolderSpinner::class) {
    private val subItems = subs.map { ItemInternal(it.id, this, it.icon, it.label, it.onClick) }
    private var expanded = false

    private fun makeCurrent(currentItem: Int): DrawerItemSpinner {
        return DrawerItemSpinner(iconExpand, iconCollapse, subs, currentItem)
    }

    override val selectable: Boolean = false

    override fun bindViewHolder(viewHolder: ViewHolderSpinner) {
        if (currentItem >= 0 && currentItem < subItems.size()) {
            viewHolder.icon.setImageDrawable(subItems[currentItem].icon)
            viewHolder.label.setText(subItems[currentItem].label)
        }

        viewHolder.itemView.setOnClickListener({

            val currPos = viewHolder.getAdapterPosition()
            if (expanded) {
                viewHolder.supervisor.removeItems(currPos + 1, subItems)

            } else {
                viewHolder.supervisor.addItems(currPos + 1, subItems)
            }
            expanded = !expanded

            viewHolder.action.setBackground(if (expanded) iconCollapse else iconExpand)
        })

        viewHolder.action.setBackground(if (expanded) iconCollapse else iconExpand)
    }

    private class ItemInternal(override val id: Long, val parentSpinner: DrawerItemSpinner, val icon: Drawable, val label: String, val onClick: (Long) -> Unit) : AdapterItemBase<ViewHolderSpinnerItem>(ViewHolderSpinnerItem::class) {
        override val selectable: Boolean = false

        override fun bindViewHolder(viewHolder: ViewHolderSpinnerItem) {
            viewHolder.icon.setImageDrawable(icon)
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

    public data class Item(val id: Long, val icon: Drawable, val label: String, val onClick: (Long) -> Unit)
}

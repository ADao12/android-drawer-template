package com.michalfaber.drawertemplate.views.adapters.drawer

import android.graphics.drawable.Drawable
import com.michalfaber.drawertemplate.views.adapters.AdapterItemBase

class DrawerItemMedium(override val id: Long, val icon: Drawable, val label: String,
                       val onClick: (Long) -> Unit, override val selectable: Boolean = true) : AdapterItemBase<ViewHolderMedium>(ViewHolderMedium::class) {
    override fun bindViewHolder(viewHolder: ViewHolderMedium) {
        viewHolder.icon.setImageDrawable(icon)
        viewHolder.label.setText(label)
        viewHolder.itemView.setOnClickListener { onClick(id) }
    }
}

class DrawerItemSmall(override val id: Long, val icon: Drawable, val label: String,
                      val onClick: (Long) -> Unit, override val selectable: Boolean = true) : AdapterItemBase<ViewHolderSmall>(ViewHolderSmall::class) {
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

// TODO: Create more generic solution
class DrawerItemSpinner(val iconExpand: Drawable, val iconCollapse: Drawable, val subs: List<DrawerItemSpinner.Item>,
                        val selectedId: Long?) : AdapterItemBase<ViewHolderSpinner>(ViewHolderSpinner::class) {
    private val subItems = subs.map { ItemInternal(it.id, this, it.icon, it.label, it.onClick) }
    private var expanded = false

    private fun newSpinnerWithId(id: Long): DrawerItemSpinner {
        return DrawerItemSpinner(iconExpand, iconCollapse, subs, id)
    }

    override val selectable: Boolean = false

    override fun bindViewHolder(viewHolder: ViewHolderSpinner) {
        subItems.filter { it.id == selectedId }
                .take(1)
                .forEach {
                    viewHolder.icon.setImageDrawable(it.icon)
                    viewHolder.label.setText(it.label)
                }

        viewHolder.itemView.setOnClickListener({
            val currPos = viewHolder.getAdapterPosition()
            if (expanded) {
                viewHolder.supervisor.removeItems(currPos + 1, subItems)
            } else {
                viewHolder.supervisor.addItems(currPos + 1, subItems)
            }

            expanded = !expanded
            showHideExpandIcon(viewHolder)
        })

        showHideExpandIcon(viewHolder)
    }

    private fun showHideExpandIcon(viewHolder: ViewHolderSpinner) {
        viewHolder.action.setBackground(if (expanded) iconCollapse else iconExpand)
    }

    private class ItemInternal(override val id: Long, val parentSpinner: DrawerItemSpinner,
                               val icon: Drawable, val label: String, val onClick: (Long) -> Unit) : AdapterItemBase<ViewHolderSpinnerItem>(ViewHolderSpinnerItem::class) {
        override val selectable: Boolean = false

        override fun bindViewHolder(viewHolder: ViewHolderSpinnerItem) {
            viewHolder.icon.setImageDrawable(icon)
            viewHolder.label.setText(label)
            viewHolder.itemView.setOnClickListener {
                val parentIdx = viewHolder.supervisor.indexOfItem(parentSpinner)
                viewHolder.supervisor.swapItem(parentIdx, parentSpinner.newSpinnerWithId(id))
                viewHolder.supervisor.removeItems(parentIdx + 1, parentSpinner.subItems)

                onClick(id)
            }
        }
    }

    public data class Item(val id: Long, val icon: Drawable, val label: String, val onClick: (Long) -> Unit)
}

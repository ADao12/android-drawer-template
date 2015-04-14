package com.michalfaber.drawertemplate.views.adapters.drawer

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.michalfaber.drawertemplate.R
import com.michalfaber.drawertemplate.views.adapters.AdapterItem
import com.michalfaber.drawertemplate.views.adapters.AdapterItemsSupervisor
import com.michalfaber.drawertemplate.views.adapters.ViewHolderProvider
import java.util.ArrayList
import java.util.HashMap
import kotlin.properties.Delegates
import kotlin.reflect.KClass

public class DrawerAdapter(val adapterItems : List<AdapterItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), AdapterItemsSupervisor<AdapterItem> {
    private val items: MutableList<AdapterItem> = adapterItems.toCollection(arrayListOf<AdapterItem>())

    private val viewHolderProvider = ViewHolderProvider()

    private var selectedItem: AdapterItem? = null

    init {
        viewHolderProvider.registerViewHolderFactory(ViewHolderSimple::class, { viewType ->
            ViewHolderSimple(viewType)
        })
        viewHolderProvider.registerViewHolderFactory(ViewHolderSection::class, { viewType ->
            ViewHolderSection(viewType)
        })
        viewHolderProvider.registerViewHolderFactory(ViewHolderSpinnerItem::class, { viewType ->
            ViewHolderSpinnerItem(viewType, this)
        })
        viewHolderProvider.registerViewHolderFactory(ViewHolderSpinnerSubItem::class, { viewType ->
            ViewHolderSpinnerSubItem(viewType, this)
        })
    }

    public fun getAdapterItemAt(adapterPosition: Int) : AdapterItem {
        if (adapterPosition >=0 && adapterPosition < items.size()) {
            return items[adapterPosition]
        } else {
            throw IllegalArgumentException()
        }
    }

    public fun select(adapterItem: AdapterItem) {
        if (adapterItem.isSelectable == true && adapterItem != selectedItem) {
            unselectPreviousAdapterItem()
            selectAdapterItem(adapterItem)
        }
    }

    private fun selectAdapterItem(adapterItem: AdapterItem) {
        selectedItem = adapterItem
        notifyItemChanged(indexOfItem(selectedItem!!));
    }

    private fun unselectPreviousAdapterItem() {
        val prevItemIndex: Int = if (selectedItem != null) indexOfItem(selectedItem!!) else -1
        if (prevItemIndex >= 0) {
            notifyItemChanged(prevItemIndex);
        }
    }

    override fun removeItems(startsFrom: Int, subItems: List<AdapterItem>) {
        items.removeAll(subItems)
        notifyItemRangeRemoved(startsFrom, subItems.size())
    }

    override fun addItems(startsFrom: Int, subItems: List<AdapterItem>) {
        items.addAll(startsFrom, subItems)
        notifyItemRangeInserted(startsFrom, subItems.size())
    }

    override fun indexOfItem(item: AdapterItem): Int {
        return items.lastIndexOf(item)
    }

    override fun swapItem(index: Int, item: AdapterItem) {
        items[index] = item
        notifyItemChanged(index)
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    override fun getItemCount(): Int {
        return items.size()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        val v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_row, viewGroup, false)
        return viewHolderProvider.provideViewHolder(v, viewType)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        items[position].bindViewHolder(viewHolder)
        viewHolder.itemView.setActivated(items[position] == selectedItem)
    }

    override fun getItemId(position: Int): Long {
        return items[position].id
    }
}

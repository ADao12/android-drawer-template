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

    val items: MutableList<AdapterItem> = adapterItems.toCollection(arrayListOf<AdapterItem>())

    val viewHolderProvider = ViewHolderProvider()

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
    }
}
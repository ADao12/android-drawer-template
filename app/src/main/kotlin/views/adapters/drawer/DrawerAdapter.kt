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
import com.michalfaber.drawertemplate.MainApplication
import com.michalfaber.drawertemplate.R
import com.michalfaber.drawertemplate.views.adapters.AdapterItem
import com.michalfaber.drawertemplate.views.adapters.AdapterItemsSupervisor
import com.michalfaber.drawertemplate.views.adapters.ViewHolderProvider
import java.util.ArrayList
import java.util.HashMap
import javax.inject.Inject
import kotlin.properties.Delegates
import kotlin.reflect.KClass

public class DrawerAdapter(val adapterItems : List<AdapterItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), AdapterItemsSupervisor<AdapterItem> {
    private val items: MutableList<AdapterItem> = adapterItems.toCollection(arrayListOf<AdapterItem>())
    private var selectedId: Long? = null

    var viewHolderProvider: ViewHolderProvider? = null
        [Inject] set

    init {
        MainApplication.graph.inject(this)

        viewHolderProvider!!.registerViewHolderFactory(ViewHolderMedium::class, R.layout.drawer_item_medium, { drawerItemView ->
            ViewHolderMedium(drawerItemView)
        })

        viewHolderProvider!!.registerViewHolderFactory(ViewHolderSmall::class, R.layout.drawer_item_small, { drawerItemView ->
            ViewHolderSmall(drawerItemView)
        })

        viewHolderProvider!!.registerViewHolderFactory(ViewHolderSeparator::class, R.layout.drawer_item_separator, { drawerItemView ->
            ViewHolderSeparator(drawerItemView)
        })

        viewHolderProvider!!.registerViewHolderFactory(ViewHolderHeader::class, R.layout.drawer_item_header, { drawerItemView ->
            ViewHolderHeader(drawerItemView)
        })

        viewHolderProvider!!.registerViewHolderFactory(ViewHolderSpinner::class, R.layout.drawer_item_spinner, { drawerItemView ->
            ViewHolderSpinner(drawerItemView, this)
        })

        viewHolderProvider!!.registerViewHolderFactory(ViewHolderSpinnerItem::class, R.layout.drawer_item_spinner_item, { drawerItemView ->
            ViewHolderSpinnerItem(drawerItemView, this)
        })
    }

    public fun getItemIdAt(adapterPosition: Int) : Long {
        if (adapterPosition >=0 && adapterPosition < items.size()) {
            return items[adapterPosition].id
        } else {
            throw IllegalArgumentException()
        }
    }

    public fun select(id: Long) {
        if (id != selectedId) {
            items.filter { it.id == id && it.selectable == true }
                    .take(1)
                    .forEach {
                        unselectPreviousAdapterItem()
                        selectAdapterItem(it)
                    }
        }
    }

    private fun selectAdapterItem(adapterItem: AdapterItem) {
        selectedId = adapterItem.id
        notifyItemChanged(indexOfItem(adapterItem));
    }

    private fun unselectPreviousAdapterItem() {
        val prevSelectedItemIdx = items.indexOfFirst { it.id == selectedId }
        if (prevSelectedItemIdx >= 0) {
            notifyItemChanged(prevSelectedItemIdx);
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
        return viewHolderProvider!!.provideViewHolder(viewGroup, viewType)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        items[position].bindViewHolder(viewHolder)
        viewHolder.itemView.setActivated(items[position].id == selectedId)
    }

    override fun getItemId(position: Int): Long {
        return items[position].id
    }
}

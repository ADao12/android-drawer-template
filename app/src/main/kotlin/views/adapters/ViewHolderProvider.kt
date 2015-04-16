package com.michalfaber.drawertemplate.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlin.reflect.KClass

/**
    Provides specific implementation of view holder based on registered class
    Holds a map where key is a ViewHolder hashCode and value is a pair: layout id and function
    which creates specific implementation of ViewHolder.
 */
class ViewHolderProvider {
    private val viewHolderFactories = hashMapOf<Int, Pair<Int, Any>>()

    [suppress("UNCHECKED_CAST")]
    fun provideViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val value = viewHolderFactories.get(viewType)
        if (value == null) {
            throw RuntimeException("Not found ViewHolder factory for viewType:$viewType")
        }
        // get the layout id and factory function
        val (layoutId: Int, f: Any) = value

        val viewHolderFactory = f as (View) -> RecyclerView.ViewHolder

        // inflate a view based on the layout ud
        val view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false)

        // create specific view holder
        return viewHolderFactory(view)
    }

    fun registerViewHolderFactory<T>(key: KClass<T>, layoutId: Int, viewHolderFactory: (View) -> T) {
        viewHolderFactories.put(key.hashCode(), Pair(layoutId, viewHolderFactory))
    }
}
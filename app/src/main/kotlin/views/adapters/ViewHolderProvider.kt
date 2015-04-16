package com.michalfaber.drawertemplate.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlin.reflect.KClass

class ViewHolderProvider {
    val viewHolderFactories = hashMapOf<Int, Pair<Int, Any>>()

    [suppress("UNCHECKED_CAST")]
    fun provideViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val value = viewHolderFactories.get(viewType)
        if (value == null) {
            throw RuntimeException("Not found ViewHolder factory for viewType:$viewType")
        }
        val (layoutId: Int, f: Any) = value
        val viewHolderFactory = f as (View) -> RecyclerView.ViewHolder
        val view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false)
        return viewHolderFactory(view)
    }

    fun registerViewHolderFactory<T>(key: KClass<T>, layoutId: Int, viewHolderFactory: (View) -> T) {
        viewHolderFactories.put(key.hashCode(), Pair(layoutId, viewHolderFactory))
    }
}
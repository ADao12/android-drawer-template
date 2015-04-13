package com.michalfaber.drawertemplate.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlin.reflect.KClass

class  ViewHolderProvider {
    val viewHolderFactories  = hashMapOf<Int, Any>()

    fun provideViewHolder(drawerItemView: View, viewType: Int) : RecyclerView.ViewHolder {
        val viewHolderFactory = viewHolderFactories.get(viewType) as (View) -> RecyclerView.ViewHolder
        return viewHolderFactory(drawerItemView)
    }

    fun registerViewHolderFactory<T>(key: KClass<T>, viewHolderFactory: (View) -> T ) {
        viewHolderFactories.put(key.hashCode(), viewHolderFactory)
    }
}
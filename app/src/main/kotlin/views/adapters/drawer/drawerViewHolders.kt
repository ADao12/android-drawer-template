package com.michalfaber.drawertemplate.views.adapters.drawer

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.michalfaber.drawertemplate.R
import com.michalfaber.drawertemplate.views.adapters.AdapterItem
import com.michalfaber.drawertemplate.views.adapters.AdapterItemsSupervisor

class ViewHolderSimple(drawerItemView: View) : RecyclerView.ViewHolder(drawerItemView) {
    val icon: ImageView = drawerItemView.findViewById(R.id.item_icon) as ImageView
    val label: TextView = drawerItemView.findViewById(R.id.item_name) as TextView
}

class ViewHolderSection(drawerItemView: View) : RecyclerView.ViewHolder(drawerItemView) {
    val label: TextView = drawerItemView.findViewById(R.id.item_name) as TextView
}

class ViewHolderSpinnerItem(drawerItemView: View, val supervisor: AdapterItemsSupervisor<AdapterItem>) : RecyclerView.ViewHolder(drawerItemView) {
    val label: TextView = drawerItemView.findViewById(R.id.item_name) as TextView
}

class ViewHolderSpinnerSubItem(drawerItemView: View, val supervisor: AdapterItemsSupervisor<AdapterItem>) : RecyclerView.ViewHolder(drawerItemView) {
    val label: TextView = drawerItemView.findViewById(R.id.item_name) as TextView
}
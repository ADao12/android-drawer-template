package com.michalfaber.drawertemplate.views.adapters.drawer

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.michalfaber.drawertemplate.R
import com.michalfaber.drawertemplate.views.adapters.AdapterItem
import com.michalfaber.drawertemplate.views.adapters.AdapterItemsSupervisor

class ViewHolderMedium(drawerItemView: View) : RecyclerView.ViewHolder(drawerItemView) {
    val icon: ImageView = drawerItemView.findViewById(R.id.icon) as ImageView
    val label: TextView = drawerItemView.findViewById(R.id.label) as TextView
}

class ViewHolderSmall(drawerItemView: View) : RecyclerView.ViewHolder(drawerItemView) {
    val icon: ImageView = drawerItemView.findViewById(R.id.icon) as ImageView
    val label: TextView = drawerItemView.findViewById(R.id.label) as TextView
}

class ViewHolderSeparator(drawerItemView: View) : RecyclerView.ViewHolder(drawerItemView)

class ViewHolderHeader(drawerItemView: View) : RecyclerView.ViewHolder(drawerItemView) {
    val label: TextView = drawerItemView.findViewById(R.id.label) as TextView
}

class ViewHolderSpinner(drawerItemView: View, val supervisor: AdapterItemsSupervisor<AdapterItem>) : RecyclerView.ViewHolder(drawerItemView) {
    val icon: ImageView = drawerItemView.findViewById(R.id.icon) as ImageView
    val label: TextView = drawerItemView.findViewById(R.id.label) as TextView
    val action: ImageView = drawerItemView.findViewById(R.id.action) as ImageView
}

class ViewHolderSpinnerItem(drawerItemView: View, val supervisor: AdapterItemsSupervisor<AdapterItem>) : RecyclerView.ViewHolder(drawerItemView) {
    val icon: ImageView = drawerItemView.findViewById(R.id.icon) as ImageView
    val label: TextView = drawerItemView.findViewById(R.id.label) as TextView
}
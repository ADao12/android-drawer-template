package com.michalfaber.drawertemplate.views.adapters

trait AdapterItemsSupervisor<T> {
    fun removeItems(startsFrom: Int, subItems: List<T>)
    fun addItems(startsFrom: Int, subItems: List<T>)
    fun indexOfItem(item: T) : Int
    fun swapItem(index: Int, item: T)
}
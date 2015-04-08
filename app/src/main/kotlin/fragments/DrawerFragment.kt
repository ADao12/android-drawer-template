package com.michalfaber.drawertemplate.fragments

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.michalfaber.drawertemplate.R
import com.michalfaber.drawertemplate.views.adapters.DrawerAdapter
import kotlinx.android.synthetic.fragment_drawer.menu_items
import views.adapters.NavigationItem
import java.util.ArrayList

public class DrawerFragment : Fragment() {

    trait NavigationDrawerListener {
        public fun onNavigationDrawerItemSelected(position: Int)
    }

    private var listener: NavigationDrawerListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_drawer, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(getActivity())
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL)
        menu_items.setLayoutManager(layoutManager)
        menu_items.setHasFixedSize(true)
        menu_items.setAdapter(DrawerAdapter(getMenu(), listener!!))
    }

    public fun getMenu(): List<NavigationItem> {
        val items = ArrayList<NavigationItem>()
        items.add(NavigationItem("item 1", getResources().getDrawable(R.drawable.ic_menu_check)))
        items.add(NavigationItem("item 2", getResources().getDrawable(R.drawable.ic_menu_check)))
        items.add(NavigationItem("item 3", getResources().getDrawable(R.drawable.ic_menu_check)))
        return items
    }

    override fun onAttach(activity: Activity?) {
        super<Fragment>.onAttach(activity)
        try {
            listener = activity as NavigationDrawerListener
        } catch (e: ClassCastException) {
            throw ClassCastException("Activity must implement NavigationDrawerCallbacks.")
        }
    }

    override fun onDetach() {
        super<Fragment>.onDetach()
        listener = null
    }
}

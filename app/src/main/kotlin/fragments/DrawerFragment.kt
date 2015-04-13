package com.michalfaber.drawertemplate.fragments

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.michalfaber.drawertemplate.R
import com.michalfaber.drawertemplate.views.adapters.AdapterItem
import com.michalfaber.drawertemplate.views.adapters.drawer.*
import kotlinx.android.synthetic.fragment_drawer.menu_items
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
        menu_items.setAdapter(DrawerAdapter(getDrawerItems()))
    }

    public fun getDrawerItems(): MutableList<AdapterItem> {

        val items: ArrayList<AdapterItem> = arrayListOf(
                DrawerItemSpinner(arrayListOf(
                        DrawerItemSpinner.DrawerItemSpinnerSubItem(getResources().getDrawable(R.drawable.ic_menu_check), "subitem 1", { Toast.makeText(getActivity(), "Subitem 1", Toast.LENGTH_SHORT).show() }),
                        DrawerItemSpinner.DrawerItemSpinnerSubItem(getResources().getDrawable(R.drawable.ic_menu_check), "subitem 2", { Toast.makeText(getActivity(), "Subitem 2", Toast.LENGTH_SHORT).show() }),
                        DrawerItemSpinner.DrawerItemSpinnerSubItem(getResources().getDrawable(R.drawable.ic_menu_check), "subitem 3", { Toast.makeText(getActivity(), "Subitem 3", Toast.LENGTH_SHORT).show() }),
                        DrawerItemSpinner.DrawerItemSpinnerSubItem(getResources().getDrawable(R.drawable.ic_menu_check), "subitem 4", { Toast.makeText(getActivity(), "Subitem 4", Toast.LENGTH_SHORT).show() }),
                        DrawerItemSpinner.DrawerItemSpinnerSubItem(getResources().getDrawable(R.drawable.ic_menu_check), "subitem 5", { Toast.makeText(getActivity(), "Subitem 5", Toast.LENGTH_SHORT).show() })
                ), 2),
                DrawerItemSimple(getResources().getDrawable(R.drawable.ic_menu_check), "standalone item", { Toast.makeText(getActivity(), "standalone item", Toast.LENGTH_SHORT).show() }),
                DrawerItemHeader("read only section"),
                DrawerItemSpinner(arrayListOf(
                        DrawerItemSpinner.DrawerItemSpinnerSubItem(getResources().getDrawable(R.drawable.ic_menu_check), "abcd 1", { Toast.makeText(getActivity(), "abcd 1", Toast.LENGTH_SHORT).show() }),
                        DrawerItemSpinner.DrawerItemSpinnerSubItem(getResources().getDrawable(R.drawable.ic_menu_check), "lorem frm 2", { Toast.makeText(getActivity(), "lorem frm 2", Toast.LENGTH_SHORT).show() }),
                        DrawerItemSpinner.DrawerItemSpinnerSubItem(getResources().getDrawable(R.drawable.ic_menu_check), "emro lore 3", { Toast.makeText(getActivity(), "emro lore 3", Toast.LENGTH_SHORT).show() }),
                        DrawerItemSpinner.DrawerItemSpinnerSubItem(getResources().getDrawable(R.drawable.ic_menu_check), "emarcade 4", { Toast.makeText(getActivity(), "emarcade 4", Toast.LENGTH_SHORT).show() }),
                        DrawerItemSpinner.DrawerItemSpinnerSubItem(getResources().getDrawable(R.drawable.ic_menu_check), "orelo lorem 5", { Toast.makeText(getActivity(), "orelo lorem 5", Toast.LENGTH_SHORT).show() })
                ), 1),
                DrawerItemSimple(getResources().getDrawable(R.drawable.ic_menu_check), "next standalone item", { Toast.makeText(getActivity(), "next standalone item", Toast.LENGTH_SHORT).show() }),
                DrawerItemSimple(getResources().getDrawable(R.drawable.ic_menu_check), "last standalone item", { Toast.makeText(getActivity(), "last standalone item", Toast.LENGTH_SHORT).show() })
        )
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

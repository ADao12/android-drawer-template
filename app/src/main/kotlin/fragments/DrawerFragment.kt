package com.michalfaber.drawertemplate.fragments

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Toast
import com.michalfaber.drawertemplate.R
import com.michalfaber.drawertemplate.views.adapters.AdapterItem
import com.michalfaber.drawertemplate.views.adapters.drawer.*
import kotlinx.android.synthetic.fragment_drawer.menu_items
import java.util.ArrayList

public class DrawerFragment : Fragment(), RecyclerView.OnItemTouchListener {
    private var listener: DrawerListener? = null
    private var gestureDetector : GestureDetectorCompat? = null
    private var drawerAdapter: DrawerAdapter? = null
    private var items: List<AdapterItem>? = null

    trait DrawerListener {
        public fun onDrawerItemSelected(id: Long)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_drawer, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super<Fragment>.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(getActivity())
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL)
        items = getDrawerItems()
        drawerAdapter = DrawerAdapter(items!!)
        menu_items.setLayoutManager(layoutManager)
        menu_items.setHasFixedSize(true)
        menu_items.setAdapter(drawerAdapter)
        menu_items.addOnItemTouchListener(this)
        drawerAdapter?.select(items!![1])
        gestureDetector = GestureDetectorCompat(getActivity(), DrawerRecyclerViewOnGestureListener());
    }

    public fun getDrawerItems(): List<AdapterItem> {

        val items: ArrayList<AdapterItem> = arrayListOf(
                DrawerItemSpinner(arrayListOf(
                        DrawerItemSpinner.DrawerItemSpinnerSubItem(1, getResources().getDrawable(R.drawable.ic_menu_check), "subitem 1", { id -> listener?.onDrawerItemSelected(id) }),
                        DrawerItemSpinner.DrawerItemSpinnerSubItem(2, getResources().getDrawable(R.drawable.ic_menu_check), "subitem 2", { id -> listener?.onDrawerItemSelected(id) }),
                        DrawerItemSpinner.DrawerItemSpinnerSubItem(3, getResources().getDrawable(R.drawable.ic_menu_check), "subitem 3", { id -> listener?.onDrawerItemSelected(id) }),
                        DrawerItemSpinner.DrawerItemSpinnerSubItem(4, getResources().getDrawable(R.drawable.ic_menu_check), "subitem 4", { id -> listener?.onDrawerItemSelected(id) }),
                        DrawerItemSpinner.DrawerItemSpinnerSubItem(5, getResources().getDrawable(R.drawable.ic_menu_check), "subitem 5", { id -> listener?.onDrawerItemSelected(id) })
                ), 2),
                DrawerItemSimple(6, getResources().getDrawable(R.drawable.ic_menu_check), "standalone item", { id -> listener?.onDrawerItemSelected(id) }),
                DrawerItemHeader("read only section"),
                DrawerItemSpinner(arrayListOf(
                        DrawerItemSpinner.DrawerItemSpinnerSubItem(7, getResources().getDrawable(R.drawable.ic_menu_check), "abcd 1", { id -> listener?.onDrawerItemSelected(id) }),
                        DrawerItemSpinner.DrawerItemSpinnerSubItem(8, getResources().getDrawable(R.drawable.ic_menu_check), "lorem frm 2", { id -> listener?.onDrawerItemSelected(id) }),
                        DrawerItemSpinner.DrawerItemSpinnerSubItem(9, getResources().getDrawable(R.drawable.ic_menu_check), "emro lore 3", { id -> listener?.onDrawerItemSelected(id) }),
                        DrawerItemSpinner.DrawerItemSpinnerSubItem(10, getResources().getDrawable(R.drawable.ic_menu_check), "emarcade 4", { id -> listener?.onDrawerItemSelected(id) }),
                        DrawerItemSpinner.DrawerItemSpinnerSubItem(11, getResources().getDrawable(R.drawable.ic_menu_check), "orelo lorem 5", { id -> listener?.onDrawerItemSelected(id) })
                ), 1),
                DrawerItemSimple(12, getResources().getDrawable(R.drawable.ic_menu_check), "next standalone item", { id -> listener?.onDrawerItemSelected(id) }),
                DrawerItemSimple(13, getResources().getDrawable(R.drawable.ic_menu_check), "last standalone item", { id -> listener?.onDrawerItemSelected(id) })
        )
        return items
    }

    override fun onAttach(activity: Activity?) {
        super<Fragment>.onAttach(activity)
        try {
            listener = activity as DrawerListener
        } catch (e: ClassCastException) {
            throw ClassCastException("Activity must implement NavigationDrawerCallbacks.")
        }
    }

    override fun onDetach() {
        super<Fragment>.onDetach()
        listener = null
    }

    override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {
    }

    override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
        gestureDetector?.onTouchEvent(e);
        return false;
    }

    inner class DrawerRecyclerViewOnGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            val view = menu_items.findChildViewUnder(e.getX(), e.getY());
            val adapterPosition = menu_items.getChildAdapterPosition(view);
            drawerAdapter?.select(drawerAdapter!!.getAdapterItemAt(adapterPosition));
            return super.onSingleTapConfirmed(e);
        }
    }
}

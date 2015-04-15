package com.michalfaber.drawertemplate.fragments

import android.app.Activity
import android.app.Fragment
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.GestureDetectorCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.michalfaber.drawertemplate.R
import com.michalfaber.drawertemplate.views.adapters.AdapterItem
import com.michalfaber.drawertemplate.views.adapters.drawer.*
import kotlinx.android.synthetic.fragment_drawer.menu_items
import java.util.ArrayList


public class DrawerFragment : Fragment(), RecyclerView.OnItemTouchListener {
    private var listener: DrawerListener? = null
    private var gestureDetector: GestureDetectorCompat? = null
    private var drawerAdapter: DrawerAdapter? = null
    private var items: List<AdapterItem>? = null

    private val handleItemSelected: (Long) -> Unit = { id ->
        listener?.onDrawerItemSelected(id)
    }

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
        drawerAdapter?.select(items!![0])
        gestureDetector = GestureDetectorCompat(getActivity(), DrawerRecyclerViewOnGestureListener());
    }

    private inline fun img(id: Int): Drawable {
        return ResourcesCompat.getDrawable(getActivity().getResources(), id, null)
    }

    public fun getDrawerItems(): List<AdapterItem> {
        val iconExpand = img(R.drawable.ic_expand_arrow)
        val iconCollapse = img(R.drawable.ic_collapse_arrow)

        val items: ArrayList<AdapterItem> = arrayListOf(
                DrawerItemHeader("Cloud"),
                DrawerItemSpinner(iconExpand, iconCollapse, arrayListOf(
                        DrawerItemSpinner.Item(ID_DROPBOX, img(R.drawable.ic_menu_dropbox), "Dropbox", handleItemSelected),
                        DrawerItemSpinner.Item(ID_BOX, img(R.drawable.ic_menu_box), "Box", handleItemSelected),
                        DrawerItemSpinner.Item(ID_ONEDRIVE, img(R.drawable.ic_menu_onedrive), "One Drive", handleItemSelected),
                        DrawerItemSpinner.Item(ID_GOOGLEDRIVE, img(R.drawable.ic_menu_googledrive), "Google Drive", handleItemSelected)
                )),
                DrawerItemHeader("Maps"),
                DrawerItemSpinner(iconExpand, iconCollapse, arrayListOf(
                        DrawerItemSpinner.Item(ID_GOOGLEEARTH, img(R.drawable.ic_menu_googleearth), "Google Earth", handleItemSelected),
                        DrawerItemSpinner.Item(ID_GOOGLEMAPS, img(R.drawable.ic_menu_googlemaps), "Google Maps", handleItemSelected),
                        DrawerItemSpinner.Item(ID_OSMAPS, img(R.drawable.ic_menu_osm), "Open Street Maps", handleItemSelected)
                )),
                DrawerItemSeparator(),
                DrawerItemMedium(ID_HOME, img(R.drawable.ic_menu_home), "Home", handleItemSelected),
                DrawerItemMedium(ID_SEARCH, img(R.drawable.ic_menu_search), "Search", handleItemSelected),
                DrawerItemMedium(ID_INBOX, img(R.drawable.ic_menu_inbox), "Inbox", handleItemSelected),
                DrawerItemMedium(ID_BOOKMARKS, img(R.drawable.ic_menu_bookmarks), "Bookmarks", handleItemSelected),
                DrawerItemSeparator(),
                DrawerItemSmall(ID_SETTINGS, img(R.drawable.ic_menu_settings), "Settings", handleItemSelected),
                DrawerItemSmall(ID_FEEDBACK, img(R.drawable.ic_menu_feedback), "Feedback", handleItemSelected),
                DrawerItemSmall(ID_ABOUT, img(R.drawable.ic_menu_about), "About", handleItemSelected),
                DrawerItemSmall(ID_LOGOUT, img(R.drawable.ic_menu_logout), "Logout", handleItemSelected, false)
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

    companion object {
        public val ID_DROPBOX: Long = 0
        public val ID_BOX: Long = 1
        public val ID_ONEDRIVE: Long = 2
        public val ID_GOOGLEDRIVE: Long = 3

        public val ID_GOOGLEEARTH: Long = 4
        public val ID_GOOGLEMAPS: Long = 5
        public val ID_OSMAPS: Long = 6

        public val ID_HOME: Long = 7
        public val ID_SEARCH: Long = 8
        public val ID_INBOX: Long = 9
        public val ID_BOOKMARKS: Long = 10

        public val ID_SETTINGS: Long = 11
        public val ID_FEEDBACK: Long = 12
        public val ID_ABOUT: Long = 13
        public val ID_LOGOUT: Long = 14
    }
}

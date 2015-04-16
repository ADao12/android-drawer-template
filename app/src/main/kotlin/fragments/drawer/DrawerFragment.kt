package com.michalfaber.drawertemplate.fragments.drawer

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
import rx.subjects.PublishSubject

public class DrawerFragment : Fragment(), RecyclerView.OnItemTouchListener {
    private var gestureDetector: GestureDetectorCompat? = null
    private var drawerAdapter: DrawerAdapter? = null
    private var items: List<AdapterItem>? = null

    private var selectedId: Long = ID_HOME
    private var selectedCloudId: Long = ID_DROPBOX
    private var selectedMapId: Long = ID_GOOGLEMAPS

    private val handleSelected: (Long) -> Unit = { id ->
        selectedId = id
        itemSelected.onNext(id)
    }

    private val handleSelectedCloud: (Long) -> Unit = { id ->
        selectedCloudId = id
        itemSelected.onNext(id)
    }

    private val handleSelectedMap: (Long) -> Unit = { id ->
        selectedMapId = id
        itemSelected.onNext(id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super<Fragment>.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            selectedId = savedInstanceState.getLong(STATE_SELECTED_ID);
            selectedCloudId = savedInstanceState.getLong(STATE_SELECTED_CLOUD_ID);
            selectedMapId = savedInstanceState.getLong(STATE_SELECTED_MAP_ID);
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(STATE_SELECTED_ID, selectedId);
        outState.putLong(STATE_SELECTED_CLOUD_ID, selectedCloudId);
        outState.putLong(STATE_SELECTED_MAP_ID, selectedMapId);

        super<Fragment>.onSaveInstanceState(outState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_drawer, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super<Fragment>.onViewCreated(view, savedInstanceState)

        // set adapter
        items = getDrawerItems()
        drawerAdapter = DrawerAdapter(items!!)
        drawerAdapter!!.select(selectedId)
        menu_items.setAdapter(drawerAdapter)

        // set layout manager
        val layoutManager = LinearLayoutManager(getActivity())
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL)
        menu_items.setLayoutManager(layoutManager)

        menu_items.setHasFixedSize(true)
        menu_items.addOnItemTouchListener(this)
        gestureDetector = GestureDetectorCompat(getActivity(), DrawerRecyclerViewOnGestureListener());
    }

    private fun img(id: Int): Drawable {
        return ResourcesCompat.getDrawable(getActivity().getResources(), id, null)
    }

    public fun getDrawerItems(): List<AdapterItem> {
        val iconExpand = img(R.drawable.ic_expand_arrow)
        val iconCollapse = img(R.drawable.ic_collapse_arrow)

        val items: List<AdapterItem> = arrayListOf(
                DrawerItemHeader("Cloud"),
                DrawerItemSpinner(iconExpand, iconCollapse, arrayListOf(
                        DrawerItemSpinner.Item(ID_DROPBOX, img(R.drawable.ic_menu_dropbox), "Dropbox", handleSelectedCloud),
                        DrawerItemSpinner.Item(ID_BOX, img(R.drawable.ic_menu_box), "Box", handleSelectedCloud),
                        DrawerItemSpinner.Item(ID_ONEDRIVE, img(R.drawable.ic_menu_onedrive), "One Drive", handleSelectedCloud),
                        DrawerItemSpinner.Item(ID_GOOGLEDRIVE, img(R.drawable.ic_menu_googledrive), "Google Drive", handleSelectedCloud)
                ), selectedCloudId),
                DrawerItemHeader("Maps"),
                DrawerItemSpinner(iconExpand, iconCollapse, arrayListOf(
                        DrawerItemSpinner.Item(ID_GOOGLEEARTH, img(R.drawable.ic_menu_googleearth), "Google Earth", handleSelectedMap),
                        DrawerItemSpinner.Item(ID_GOOGLEMAPS, img(R.drawable.ic_menu_googlemaps), "Google Maps", handleSelectedMap),
                        DrawerItemSpinner.Item(ID_OSMAPS, img(R.drawable.ic_menu_osm), "Open Street Maps", handleSelectedMap)
                ), selectedMapId),
                DrawerItemSeparator(),
                DrawerItemMedium(ID_HOME, img(R.drawable.ic_menu_home), "Home", handleSelected),
                DrawerItemMedium(ID_SEARCH, img(R.drawable.ic_menu_search), "Search", handleSelected),
                DrawerItemMedium(ID_INBOX, img(R.drawable.ic_menu_inbox), "Inbox", handleSelected),
                DrawerItemMedium(ID_BOOKMARKS, img(R.drawable.ic_menu_bookmarks), "Bookmarks", handleSelected),
                DrawerItemSeparator(),
                DrawerItemSmall(ID_SETTINGS, img(R.drawable.ic_menu_settings), "Settings", handleSelected),
                DrawerItemSmall(ID_FEEDBACK, img(R.drawable.ic_menu_feedback), "Feedback", handleSelected),
                DrawerItemSmall(ID_ABOUT, img(R.drawable.ic_menu_about), "About", handleSelected),
                DrawerItemSmall(ID_LOGOUT, img(R.drawable.ic_menu_logout), "Logout", handleSelected, false)
        )
        return items
    }

    override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {
    }

    override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
        gestureDetector?.onTouchEvent(e);
        return false;
    }

    inner class DrawerRecyclerViewOnGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {

            // get the position of touched item
            val view = menu_items.findChildViewUnder(e.getX(), e.getY());
            val adapterPosition = menu_items.getChildAdapterPosition(view);

            // set this item as selected
            drawerAdapter?.select(drawerAdapter!!.getItemIdAt(adapterPosition));

            return super.onSingleTapConfirmed(e);
        }
    }

    companion object {
        private val STATE_SELECTED_ID = "STATE_SELECTED_ID"
        private val STATE_SELECTED_CLOUD_ID = "STATE_SELECTED_CLOUD_ID"
        private val STATE_SELECTED_MAP_ID = "STATE_SELECTED_MAP_ID"
        private var nextId: Long = 0;

        public val ID_DROPBOX: Long = ++nextId
        public val ID_BOX: Long = ++nextId
        public val ID_ONEDRIVE: Long = ++nextId
        public val ID_GOOGLEDRIVE: Long = ++nextId

        public val ID_GOOGLEEARTH: Long = ++nextId
        public val ID_GOOGLEMAPS: Long = ++nextId
        public val ID_OSMAPS: Long = ++nextId

        public val ID_HOME: Long = ++nextId
        public val ID_SEARCH: Long = ++nextId
        public val ID_INBOX: Long = ++nextId
        public val ID_BOOKMARKS: Long = ++nextId

        public val ID_SETTINGS: Long = ++nextId
        public val ID_FEEDBACK: Long = ++nextId
        public val ID_ABOUT: Long = ++nextId
        public val ID_LOGOUT: Long = ++nextId

        // emits ids of selected drawer items
        public val itemSelected: PublishSubject<Long> = PublishSubject.create()
    }
}
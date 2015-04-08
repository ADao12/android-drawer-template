package com.michalfaber.drawertemplate.views.adapters

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.michalfaber.drawertemplate.R
import com.michalfaber.drawertemplate.fragments.DrawerFragment.NavigationDrawerListener
import views.adapters.NavigationItem

public class NavigationDrawerAdapter(val items: List<NavigationItem>, val navigationDrawerCallbacks: NavigationDrawerListener) : RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder>() {

    private var mSelectedPosition: Int = 0
    private var mTouchedPosition = -1

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): NavigationDrawerAdapter.ViewHolder {
        val v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_row, viewGroup, false)
        val viewHolder = ViewHolder(v)
        viewHolder.itemView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {

                when (event.getAction()) {
                    MotionEvent.ACTION_DOWN -> {
                        touchPosition(viewHolder.getPosition())
                        return false
                    }
                    MotionEvent.ACTION_CANCEL -> {
                        touchPosition(-1)
                        return false
                    }
                    MotionEvent.ACTION_MOVE -> return false
                    MotionEvent.ACTION_UP -> {
                        touchPosition(-1)
                        return false
                    }
                }
                return true
            }
        })
        viewHolder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                if (navigationDrawerCallbacks != null)
                    navigationDrawerCallbacks!!.onNavigationDrawerItemSelected(viewHolder.getPosition())
            }
        })

        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: NavigationDrawerAdapter.ViewHolder, i: Int) {
        viewHolder.textView.setText(items!!.get(i).text)
        viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(items.get(i).drawable, null, null, null)

        //TODO: selected menu position, change layout accordingly
        if (mSelectedPosition == i || mTouchedPosition == i) {
            viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(R.color.selected_gray))
        } else {
            viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    private fun touchPosition(position: Int) {
        val lastPosition = mTouchedPosition
        mTouchedPosition = position
        if (lastPosition >= 0)
            notifyItemChanged(lastPosition)
        if (position >= 0)
            notifyItemChanged(position)
    }

    public fun selectPosition(position: Int) {
        val lastPosition = mSelectedPosition
        mSelectedPosition = position
        notifyItemChanged(lastPosition)
        notifyItemChanged(position)
    }

    override fun getItemCount(): Int {
        return if (items != null) items.size() else 0
    }

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public var textView: TextView

        {
            textView = itemView.findViewById(R.id.item_name) as TextView
        }
    }
}

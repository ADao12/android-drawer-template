package com.michalfaber.drawertemplate.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.michalfaber.drawertemplate.R
import com.michalfaber.drawertemplate.fragments.DrawerFragment.NavigationDrawerListener
import views.adapters.NavigationItem

public class DrawerAdapter(val items: List<NavigationItem>, val navigationDrawerCallbacks: NavigationDrawerListener) : RecyclerView.Adapter<DrawerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DrawerAdapter.ViewHolder {
        val v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_row, viewGroup, false)
        val viewHolder = ViewHolder(v)
        viewHolder.itemView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {

                when (event.getAction()) {
                    MotionEvent.ACTION_DOWN -> {

                        return false
                    }
                    MotionEvent.ACTION_CANCEL -> {

                        return false
                    }
                    MotionEvent.ACTION_MOVE -> return false
                    MotionEvent.ACTION_UP -> {

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

    override fun onBindViewHolder(viewHolder: DrawerAdapter.ViewHolder, i: Int) {
        viewHolder.textView.setText(items!!.get(i).text)
        viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(items.get(i).drawable, null, null, null)
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

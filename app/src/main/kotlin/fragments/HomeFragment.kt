package com.michalfaber.drawertemplate.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.*
import com.michalfaber.drawertemplate.R

public class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.action_item1 -> return true
            R.id.action_item2 -> return true
            R.id.action_item3 -> return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        public fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            return fragment
        }
    }
}
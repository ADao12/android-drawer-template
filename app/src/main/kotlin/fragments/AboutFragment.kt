package com.michalfaber.drawertemplate.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.michalfaber.drawertemplate.R

public class AboutFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    companion object {
        public fun newInstance(): AboutFragment {
            val fragment = AboutFragment()
            val args = Bundle()
            fragment.setArguments(args)
            return fragment
        }
    }
}
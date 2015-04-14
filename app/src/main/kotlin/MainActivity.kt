package com.michalfaber.drawertemplate

import android.content.SharedPreferences
import android.content.res.Configuration
import android.support.v7.app.ActionBarActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.support.v7.widget.Toolbar

import android.support.v4.widget.DrawerLayout
import android.support.v7.app
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.michalfaber.drawertemplate.fragments.DrawerFragment

import kotlinx.android.synthetic.activity_main.*
import javax.inject.Inject
import kotlin.properties.Delegates

public class MainActivity : ActionBarActivity(), DrawerFragment.DrawerListener {

    var preferences: SharedPreferences? = null
        [Inject] set

    var actionBarDrawerToggle: ActionBarDrawerToggle? = null

    val drawerContainer by  Delegates.lazy {
        findViewById(R.id.fragment_drawer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super<ActionBarActivity>.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_actionbar)

        MainApplication.graph.inject(this)

        actionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawer, toolbar_actionbar, R.string.drawer_open, R.string.drawer_close) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                //getActionBar().setTitle(getTitle());
                invalidateOptionsMenu() // calls onPrepareOptionsMenu()
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                //getActionBar().setTitle("OPENED !!!");
                invalidateOptionsMenu() // calls onPrepareOptionsMenu()
            }
        }

        drawer.setDrawerListener(actionBarDrawerToggle)
    }

    override public fun onDrawerItemSelected(id: Long) {
        Toast.makeText(this, "Menu item selected id:${id}", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(drawerContainer)) {
            drawer.closeDrawer(drawerContainer)
        } else {
            super<ActionBarActivity>.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (!drawer.isDrawerOpen(drawerContainer)) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_main, menu)
            return true
        }

        return super<ActionBarActivity>.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        // If the nav drawer is open, hide action items related to the content view
        val drawerOpen = drawer.isDrawerOpen(drawerContainer);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super<ActionBarActivity>.onPrepareOptionsMenu(menu);
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super<ActionBarActivity>.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle?.syncState();
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super<ActionBarActivity>.onConfigurationChanged(newConfig);
        actionBarDrawerToggle?.onConfigurationChanged(newConfig);
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (actionBarDrawerToggle?.onOptionsItemSelected(item) == true) {
            return true;
        }

        if (item.getItemId() == R.id.action_settings) {
            return true
        }

        return super<ActionBarActivity>.onOptionsItemSelected(item);
    }
}

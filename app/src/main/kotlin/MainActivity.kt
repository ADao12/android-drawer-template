package com.michalfaber.drawertemplate

import android.app.Fragment
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.ActionBarActivity
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.widget.Toast
import com.michalfaber.drawertemplate.fragments.AboutFragment
import com.michalfaber.drawertemplate.fragments.HomeFragment
import com.michalfaber.drawertemplate.fragments.drawer.DrawerFragment
import kotlinx.android.synthetic.activity_main.drawer
import kotlinx.android.synthetic.activity_main.toolbar_actionbar
import rx.Observer
import rx.Subscription
import rx.android.app.AppObservable.bindActivity
import javax.inject.Inject
import kotlin.properties.Delegates
import kotlin.reflect.KClass

public class MainActivity : ActionBarActivity(), Observer<Long> {
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    private var subscription: Subscription? = null
    private val drawerContainer by  Delegates.lazy {
        findViewById(R.id.fragment_drawer)
    }

    var preferences: SharedPreferences? = null
        [Inject] set

    override fun onCreate(savedInstanceState: Bundle?) {
        super<ActionBarActivity>.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_actionbar as Toolbar)

        MainApplication.graph.inject(this)

        initDrawer()

        setDrawerDefaultItem(savedInstanceState)
    }

    override fun onDestroy() {
        subscription?.unsubscribe();
        super<ActionBarActivity>.onDestroy()
    }

    override fun onCompleted() {
    }

    private fun initDrawer() {
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawer, toolbar_actionbar as Toolbar,
                R.string.drawer_open, R.string.drawer_close)
        drawer.setDrawerListener(actionBarDrawerToggle)
        subscription = bindActivity(this, DrawerFragment.itemSelected).subscribe(this);
    }

    private fun setDrawerDefaultItem(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            DrawerFragment.itemSelected.onNext(DrawerFragment.ID_HOME)
        }
    }

    private fun showFragment<A : Fragment>(fragmentTag: KClass<A>, args: Bundle?, newInstance: () -> A) {
        val fragment = getFragmentManager().findFragmentByTag(fragmentTag.toString()) ?: newInstance()
        if (args != null) {
            fragment.setArguments(args)
        }
        getFragmentManager().beginTransaction().replace(R.id.container, fragment, fragmentTag.toString()).commit()
    }

    private fun closeDrawer() {
        if (drawer.isDrawerOpen(drawerContainer)) {
            drawer.closeDrawer(drawerContainer)
        }
    }

    override fun onNext(id: Long) {
        when (id) {
            DrawerFragment.ID_HOME -> {
                closeDrawer()
                showFragment(HomeFragment::class, null, { HomeFragment.newInstance() })
            }
            DrawerFragment.ID_ABOUT -> {
                closeDrawer()
                showFragment(AboutFragment::class, null, { AboutFragment.newInstance() })
            }
            else -> Toast.makeText(this, "Drawer item selected id:${id}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onError(e: Throwable?) {
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(drawerContainer)) {
            drawer.closeDrawer(drawerContainer)
        } else {
            super<ActionBarActivity>.onBackPressed()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super<ActionBarActivity>.onPostCreate(savedInstanceState);
        actionBarDrawerToggle?.syncState();
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super<ActionBarActivity>.onConfigurationChanged(newConfig);
        actionBarDrawerToggle?.onConfigurationChanged(newConfig);
    }
}

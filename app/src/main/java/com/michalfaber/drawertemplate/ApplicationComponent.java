package com.michalfaber.drawertemplate;

import com.michalfaber.drawertemplate.views.adapters.drawer.DrawerAdapter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AndroidModule.class)
public interface ApplicationComponent {
    void inject(MainApplication application);

    void inject(MainActivity mainActivity);

    void inject(DrawerAdapter drawerAdapter);
}

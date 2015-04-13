package com.michalfaber.drawertemplate;

//import javax.inject.Singleton;

//import dagger.Component;

//@Singleton
//@Component(modules = AndroidModule.class)
public interface ApplicationComponent {
    void inject(MainApplication application);

    void inject(MainActivity mainActivity);
}

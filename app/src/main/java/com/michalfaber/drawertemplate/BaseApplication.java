package com.michalfaber.drawertemplate;

import android.app.Application;

public abstract class BaseApplication extends Application {
    protected ApplicationComponent createApplicationComponent() {
        return DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this)).build();
    }
}

package com.waracle.androidtest;

import android.app.Application;

import com.waracle.androidtest.DataSource.DataSourceManager;
import com.waracle.androidtest.Storage.ImageCache;

public class MainApplication extends Application {

    private static MainApplication application;
    private DataSourceManager dataSourceManager = new DataSourceManager();
    private ImageCache imageCache = new ImageCache();

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        imageCache.initializeCache();
    }

    public static MainApplication getInstance(){
        return application;
    }

    public static DataSourceManager getDataSource(){
        return getInstance().dataSourceManager;
    }

    public static ImageCache getImageCache(){
        return getInstance().imageCache;
    }

}

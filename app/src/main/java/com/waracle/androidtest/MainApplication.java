package com.waracle.androidtest;

import android.app.Application;

import com.waracle.androidtest.DataSource.DataSourceManager;

public class MainApplication extends Application {

    private static MainApplication application;
    private DataSourceManager dataSourceManager = new DataSourceManager();

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static MainApplication getInstance(){
        return application;
    }

    public static DataSourceManager getDataSource(){
        return getInstance().dataSourceManager;
    }

}

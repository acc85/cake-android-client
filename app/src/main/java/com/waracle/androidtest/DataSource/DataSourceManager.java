package com.waracle.androidtest.DataSource;

import android.os.AsyncTask;

import com.waracle.androidtest.MainApplication;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


import androidx.annotation.NonNull;

public class DataSourceManager {

    private static long timeToCache = 1000000L;
    private ConcurrentMap<String, DataSources> dataSourceConcurrentMap = new ConcurrentHashMap<>();

    public long getTimeToCache() {
        return timeToCache;
    }

    public static void setTimeToCache(long timeToCache) {
        DataSourceManager.timeToCache = timeToCache;
    }

    public DataSources getDataSource(String key){
        return dataSourceConcurrentMap.get(key);
    }

    public void clearDataSources(){
        dataSourceConcurrentMap.clear();
        MainApplication.getImageCache().clearCache();
    }

    @SuppressWarnings("unchecked")
    public void addToMap(@NonNull String url, @NonNull DataSources dataSource, @NonNull DataSources.DataListeners imageSourceListener) {
        DataSources storedDataSource = dataSourceConcurrentMap.get(url);
        if (storedDataSource == null) {
            dataSourceConcurrentMap.put(url, dataSource);
        } else {
            dataSource = storedDataSource;
        }
        dataSource.getDataSourceListeners().add(new WeakReference<>(imageSourceListener));
        final DataSources finalDataSource = dataSource;
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                finalDataSource.run();
            }
        });
    }


}

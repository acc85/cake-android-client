package com.waracle.androidtest.DataSource;

import android.graphics.Bitmap;

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

    @SuppressWarnings("unchecked")
    public void addToMap(@NonNull String url, @NonNull DataSources dataSources, @NonNull DataSources.DataListeners imageSourceListener){
        DataSources storedDataSource = dataSourceConcurrentMap.get(url);
        if(storedDataSource == null) {
            dataSourceConcurrentMap.put(url, dataSources);
        }
        dataSources.getDataSourceListeners().add(new WeakReference<>(imageSourceListener));
        dataSources.run();
    }


}

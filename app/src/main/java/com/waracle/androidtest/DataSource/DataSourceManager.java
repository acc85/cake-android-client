package com.waracle.androidtest.DataSource;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import androidx.annotation.NonNull;

public class DataSourceManager {

    @SuppressWarnings("FieldCanBeLocal")
    private long timeToCache = 1000000L;
    private ConcurrentMap<String, DataSources> dataSourceConcurrentMap = new ConcurrentHashMap<>();

    long getTimeToCache() {
        return timeToCache;
    }


    public void clearDataSources() {
        dataSourceConcurrentMap.clear();
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

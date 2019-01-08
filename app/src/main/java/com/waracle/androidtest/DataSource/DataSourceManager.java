package com.waracle.androidtest.DataSource;

import android.graphics.Bitmap;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import androidx.annotation.NonNull;

public class DataSourceManager {

    private static long timeToCache = 1000000L;
    private ConcurrentMap<String, DataSource> imageSourceConcurrentMap = new ConcurrentHashMap<>();

    public long getTimeToCache() {
        return timeToCache;
    }

    public static void setTimeToCache(long timeToCache) {
        DataSourceManager.timeToCache = timeToCache;
    }

    @SuppressWarnings("unchecked")
    public void addToMap(@NonNull String url, @NonNull DataSource dataSource, @NonNull DataSource.DataListeners<Bitmap> imageSourceListener){
        DataSource storedDataSource = imageSourceConcurrentMap.get(url);
        if(storedDataSource == null) {
            imageSourceConcurrentMap.put(url, dataSource);
        }
        dataSource.getDataSourceListeners().add(new WeakReference<>(imageSourceListener));
        dataSource.run();
    }


//    public void addToMap(String url, DataSource.DataListeners imageSourceListener){
//        ImageSource imageSource = imageSourceConcurrentMap.get(url);
//        if(imageSource == null) {
//            imageSource = new ImageSource();
//            imageSource.setUrl(url);
//            imageSourceConcurrentMap.put(url, imageSource);
//        }
//        try {
//            imageSource.getDataSourceListeners().add(new WeakReference<DataSource.DataListeners>(imageSourceListener));
//            imageSource.getImage();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}

package com.waracle.androidtest.DataSource;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public interface DataSources<T> {

    String getUrl();

    List<WeakReference<DataListeners<T>>> getDataSourceListeners();

    void run();

    interface DataListeners<R>{

        void onDataRetrieved(R result);

    }
}
package com.waracle.androidtest.DataSource;

import com.waracle.androidtest.DataSource.DataSource;

import java.lang.ref.WeakReference;
import java.util.List;

public class TextDataSource implements DataSource<String> {

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public List<WeakReference<DataListeners<String>>> getDataSourceListeners() {
        return null;
    }

    @Override
    public void run() {

    }
}

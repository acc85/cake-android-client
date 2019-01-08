package com.waracle.androidtest.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.waracle.androidtest.Adapters.MyAdapter;
import com.waracle.androidtest.DataSource.CakeDataSources;
import com.waracle.androidtest.DataSource.DataSources;
import com.waracle.androidtest.MainApplication;
import com.waracle.androidtest.Model.CakeModel;
import com.waracle.androidtest.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

/**
 * Fragment is responsible for loading in some JSON and
 * then displaying a list of cakes with images.
 * Fix any crashes
 * Improve any performance issues
 * Use good coding practices to make code more secure
 */
public class PlaceholderFragment extends ListFragment {

    public static final String TAG = PlaceholderFragment.class.getSimpleName();

    @SuppressWarnings("FieldCanBeLocal")
    private DataSources.DataListeners<List<CakeModel>> dataListener;

    private ListView mListView;
    private MyAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mListView = rootView.findViewById(android.R.id.list);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Create and set the list adapter.
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        dataListener = new DataSources.DataListeners<List<CakeModel>>() {
            @Override
            public void onDataRetrieved(final List<CakeModel> result) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setItems(result);
                    }
                });
            }
        };

        String JSON_URL = "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";
        MainApplication.getDataSource().addToMap(JSON_URL, new CakeDataSources(), dataListener);
    }

    public void refreshList(){
        MainApplication.getDataSource().clearDataSources();
        getData();
    }

}

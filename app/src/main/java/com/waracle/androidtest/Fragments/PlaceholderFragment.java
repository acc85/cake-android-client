package com.waracle.androidtest.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.waracle.androidtest.DataSource.CakeDataSources;
import com.waracle.androidtest.DataSource.DataSources;
import com.waracle.androidtest.ImageLoader;
import com.waracle.androidtest.MainApplication;
import com.waracle.androidtest.Model.CakeModel;
import com.waracle.androidtest.R;

import java.util.ArrayList;
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

    private class MyAdapter extends BaseAdapter {

        // Can you think of a better way to represent these items???
        private List<CakeModel> mItems = new ArrayList<>();

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public CakeModel getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View root = inflater.inflate(R.layout.list_item_layout, parent, false);
            if (root != null) {
                TextView title = root.findViewById(R.id.title);
                TextView desc = root.findViewById(R.id.desc);
                ImageView image = root.findViewById(R.id.image);

                CakeModel object = getItem(position);
                title.setText(object.getTitle());
                desc.setText(object.getDesc());
                ImageLoader imageLoader = new ImageLoader();
                image.setTag(R.id.loader, imageLoader);
                imageLoader.load(object.getImageUrl(), image);
            }

            return root;
        }

        public void setItems(List<CakeModel> items) {
            mItems = items;
            notifyDataSetChanged();
        }
    }
}
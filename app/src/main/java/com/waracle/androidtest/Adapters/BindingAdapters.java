package com.waracle.androidtest.Adapters;

import android.widget.ImageView;

import com.waracle.androidtest.ImageLoader;
import com.waracle.androidtest.viewModels.CakeViewModel;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BindingAdapters {

    @BindingAdapter("imageUrl")
    public static void getBitmapForImageView(ImageView imageView, String url) {
        ImageLoader imageLoader = new ImageLoader();
        imageView.setTag(imageLoader);
        imageLoader.load(url, imageView);
    }

    @BindingAdapter("setupRecyclerView")
    public static void setAdapter(RecyclerView recyclerView, CakeViewModel cakeViewModel) {
        MainAdapter mainAdapter = new MainAdapter();
        mainAdapter.setCakeViewModel(cakeViewModel);
        recyclerView.setAdapter(mainAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());
        ((LinearLayoutManager) layoutManager).setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
        cakeViewModel.fetchCakeModels();
    }
}

package com.waracle.androidtest.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.waracle.androidtest.ImageLoader
import com.waracle.androidtest.viewModels.CakeViewModel


@BindingAdapter("imageUrl")
fun getBitmapForImageView(imageView: ImageView, url: String) {
    val imageLoader = ImageLoader()
    imageView.tag = imageLoader
    imageLoader.load(url, imageView)
}

@BindingAdapter("setupRecyclerView")
fun setAdapter(recyclerView: RecyclerView, cakeViewModel: CakeViewModel) {
    val mainAdapter = MainAdapter()
    mainAdapter.cakeViewModel = cakeViewModel
    recyclerView.adapter = mainAdapter
    val layoutManager = LinearLayoutManager(recyclerView.context)
    layoutManager.orientation = RecyclerView.VERTICAL
    recyclerView.layoutManager = layoutManager
    recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
    cakeViewModel.fetchCakeModels()
}

package com.waracle.androidtest.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.waracle.androidtest.ImageLoader;
import com.waracle.androidtest.Model.CakeModel;
import com.waracle.androidtest.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainRecyclerViewHolder> {

    List<CakeModel> cakeModels = new ArrayList<>();

    public void setItems(List<CakeModel> cakeModels){
        this.cakeModels = cakeModels;
    }

    @NonNull
    @Override
    public MainRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout,parent,false);
        return new MainRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerViewHolder holder, int position) {
        holder.title.setText(cakeModels.get(position).getTitle());
        holder.desc.setText((cakeModels.get(position).getDesc()));
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.load(cakeModels.get(position).getImageUrl(), holder.image);
    }

    @Override
    public int getItemCount() {
        return cakeModels.size();
    }

    class MainRecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView desc;
        ImageView image;
        public MainRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            image = itemView.findViewById(R.id.image);
        }
    }
}

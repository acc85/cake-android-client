package com.waracle.androidtest.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.waracle.androidtest.BR;
import com.waracle.androidtest.Model.CakeModel;
import com.waracle.androidtest.R;
import com.waracle.androidtest.databinding.ListItemLayoutBinding;
import com.waracle.androidtest.viewModels.CakeViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainRecyclerViewHolder> {

    private List<CakeModel> cakeModels = new ArrayList<>();

    private CakeViewModel cakeViewModel;

    public void setCakeViewModel(CakeViewModel cakeViewModel) {
        this.cakeViewModel = cakeViewModel;
    }

    public void setItems(List<CakeModel> cakeModels) {
        this.cakeModels = cakeModels;
        notifyDataSetChanged();
    }

    public void clear() {
        notifyItemRangeRemoved(0, this.cakeModels.size());
        this.cakeModels.clear();
    }

    @NonNull
    @Override
    public MainRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemLayoutBinding listItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item_layout, parent, false);
        return new MainRecyclerViewHolder(listItemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerViewHolder holder, int position) {
        holder.binding(position);
        holder.image.setImageBitmap(null);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull MainRecyclerViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public int getItemCount() {
        return cakeModels.size();
    }

    class MainRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView desc;
        ImageView image;
        ViewDataBinding viewDataBinding;

        MainRecyclerViewHolder(@NonNull ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            image = itemView.findViewById(R.id.image);
        }

        private void binding(int position) {
            this.viewDataBinding.setVariable(BR.position, position);
            this.viewDataBinding.setVariable(BR.cakeViewModel, cakeViewModel);
        }
    }
}

package com.waracle.androidtest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.waracle.androidtest.BR
import com.waracle.androidtest.model.CakeModel
import com.waracle.androidtest.R
import com.waracle.androidtest.databinding.ListItemLayoutBinding
import com.waracle.androidtest.viewModels.CakeViewModel
import java.util.ArrayList

class MainAdapter: RecyclerView.Adapter<MainAdapter.MainRecyclerViewHolder>() {

    private var cakeModels: MutableList<CakeModel> = ArrayList()

    var cakeViewModel: CakeViewModel? = null

    fun setItems(cakeModels: MutableList<CakeModel>) {
        this.cakeModels = cakeModels
        notifyDataSetChanged()
    }

    fun clear() {
        notifyItemRangeRemoved(0, this.cakeModels.size)
        this.cakeModels.clear()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewHolder {
        val listItemLayoutBinding = DataBindingUtil.inflate<ListItemLayoutBinding>(LayoutInflater.from(parent.context), R.layout.list_item_layout, parent, false)
        return MainRecyclerViewHolder(listItemLayoutBinding)
    }

    override fun getItemCount(): Int {
        return cakeModels.size
    }

    override fun onBindViewHolder(holder: MainRecyclerViewHolder, position: Int) {
        holder.binding(position)
        holder.image.setImageBitmap(null)
        holder.itemView.setOnClickListener { }
    }


    inner class MainRecyclerViewHolder(private var viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        var title: TextView = itemView.findViewById(R.id.title)
        var desc: TextView = itemView.findViewById(R.id.desc)
        var image: ImageView = itemView.findViewById(R.id.image)

        fun binding(position: Int) {
            this.viewDataBinding.setVariable(BR.position, position)
            this.viewDataBinding.setVariable(BR.cakeViewModel,cakeViewModel)
        }
    }
}
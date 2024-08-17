package com.example.tinderfordogs.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tinderfordogs.R
import com.example.tinderfordogs.databinding.ItemTinderCardBinding
import com.example.tinderfordogs.model.TinderCardModel

class DogTinderAdapter(val context:Context, var list:List<TinderCardModel>):RecyclerView.Adapter<DogTinderAdapter.ViewHolder>() {


    fun updateData(newImages: List<TinderCardModel>) {
        list = newImages
        notifyDataSetChanged()
    }
    inner class ViewHolder(val binding:ItemTinderCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTinderCardBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context).load(list[position].message).error(R.drawable.img_login_top).into(holder.binding.cardImage)
    }
}
package com.example.tinderfordogs.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tinderfordogs.R
import com.example.tinderfordogs.databinding.ItemDogPicBinding
import com.example.tinderfordogs.databinding.ItemTinderCardBinding
import com.example.tinderfordogs.model.TinderCardModel


class FavDogsAdapter(private val context: Context, private val mList: List<TinderCardModel>) : RecyclerView.Adapter<FavDogsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(ItemDogPicBinding.inflate(LayoutInflater.from(context),parent,false))

    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        Glide.with(context).load(ItemsViewModel.message).error(R.drawable.img_login_top).into(holder.binding.ivDogPic)


    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ViewHolder(val binding: ItemDogPicBinding):RecyclerView.ViewHolder(binding.root)

}
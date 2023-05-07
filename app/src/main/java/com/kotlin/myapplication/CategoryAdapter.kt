package com.kotlin.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.myapplication.databinding.ItemCategoryOneBinding

class CategoryAdapter(private var categoryList: ArrayList<Category>): RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemCategoryOneBinding = ItemCategoryOneBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = categoryList[position]
        holder.binding.shop.text = currentItem.category

    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class MyViewHolder(val binding: ItemCategoryOneBinding): RecyclerView.ViewHolder(binding.root) {
    }
}
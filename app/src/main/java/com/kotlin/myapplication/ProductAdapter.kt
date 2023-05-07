package com.kotlin.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.myapplication.databinding.ItemProductBinding

class ProductAdapter(private var productList: ArrayList<ProductData>): RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    private lateinit var mLisetener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mLisetener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemProductBinding = ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding,mLisetener)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = productList[position]
        currentItem.image?.let { holder.binding.image.setImageResource(it) }
        holder.binding.nameProd.text = currentItem.name
        holder.binding.priceProd.text = currentItem.price


    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class MyViewHolder(val binding: ItemProductBinding, listener: onItemClickListener): RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}
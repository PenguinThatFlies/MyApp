package com.kotlin.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.myapplication.databinding.ItemBestBinding

class BestAdapter(private var bestList: ArrayList<BestData>): RecyclerView.Adapter<BestAdapter.MyViewHolder>() {

    private lateinit var mLisetener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mLisetener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemBestBinding = ItemBestBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding,mLisetener)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = bestList[position]
        currentItem.image?.let { holder.binding.best.setImageResource(it) }
    }

    override fun getItemCount(): Int {
        return bestList.size
    }

    class MyViewHolder(val binding: ItemBestBinding, listener: onItemClickListener): RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}
package com.kotlin.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.myapplication.databinding.ItemNewsBinding

class NewsAdapter(private var newsList: ArrayList<NewsData>): RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    private lateinit var mLisetener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mLisetener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ItemNewsBinding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding,mLisetener)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newsList[position]
        currentItem.image?.let { holder.binding.news.setImageResource(it) }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class MyViewHolder(val binding: ItemNewsBinding, listener: onItemClickListener): RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}
package com.kotlin.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.myapplication.databinding.FragmentProductBinding

class ProductFragment : Fragment(R.layout.fragment_product) {
    private lateinit var binding : FragmentProductBinding
    private lateinit var newArrayList: ArrayList<ProductData>
    private lateinit var newCategoryArrayList: ArrayList<Category>
    lateinit var imageId: Array<Int>
    private lateinit var nameList: Array<String>
    private lateinit var priceList: Array<String>
    private lateinit var categoryList: Array<String>
    lateinit var uid: Array<Int>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductBinding.bind(view)


        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.setHasFixedSize(true)

        binding.recyclerViewtwo.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,true)
        binding.recyclerViewtwo.setHasFixedSize(true)

        imageId = arrayOf(
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4
        )

        nameList = arrayOf(
            "Irakli",
            "Vano",
            "Shako",
            "Saba",
            "Irakli",
            "Vano",
            "Shako",
            "Saba"
        )

        priceList = arrayOf(
            "16$",
            "17$",
            "3$",
            "5$",
            "16$",
            "17$",
            "3$",
            "5$"
        )

        categoryList = arrayOf(
            "Irakli",
            "Vano",
            "Shako",
            "Saba",
            "Irakli",
            "Vano",
            "Shako",
            "Saba"
        )

        newArrayList = arrayListOf<ProductData>()
        newCategoryArrayList = arrayListOf<Category>()
        getUser()
        getCategory()

    }

    private fun getCategory(){
        for (i in categoryList.indices){
            val dd = Category(categoryList[i])
            newCategoryArrayList.add(dd)
        }
        val catadapter = CategoryAdapter(newCategoryArrayList)
        binding.recyclerViewtwo.adapter = catadapter
    }

    private fun getUser(){
        for (i in imageId.indices){
            val data = ProductData(imageId[i], nameList[i], priceList[i]) //, nameList[i]
            newArrayList.add(data)
        }
        val adapter = ProductAdapter(newArrayList)
        binding.recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : ProductAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                activity?.let{
                    val intent = Intent (it, ActivityProductPage::class.java)
                    it.startActivity(intent)
                    intent.putExtra("ImageId", newArrayList[position].image)
                    intent.putExtra("name", newArrayList[position].name)
                    intent.putExtra("price", newArrayList[position].price)
                    startActivity(intent)
                }
            }
        })
    }
}
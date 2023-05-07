package com.kotlin.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.myapplication.databinding.ActivityProductPageBinding

class ActivityProductPage:AppCompatActivity() {
    lateinit var binding: ActivityProductPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        val img = bundle!!.getInt("imageId")
        val name = bundle.getInt("name")
        val price = bundle.getInt("price")

        binding.image.setImageResource(img)
        binding.productName.text = name.toString()
        binding.productPrice.text = price.toString()

    }
}
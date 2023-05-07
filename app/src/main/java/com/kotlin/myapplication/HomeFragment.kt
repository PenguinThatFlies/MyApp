package com.kotlin.myapplication

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kotlin.myapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home){
    private lateinit var binding: FragmentHomeBinding
    private val auth = Firebase.auth
    private val db2 = FirebaseDatabase.getInstance().getReference("User")
    val db = Firebase.firestore

    private lateinit var bestData: ArrayList<BestData>
    private lateinit var newsData: ArrayList<NewsData>
    lateinit var imageIdbest: Array<Int>
    lateinit var imageIdnews: Array<Int>
    private lateinit var nameList: Array<String>
    private lateinit var priceList: Array<String>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,true)
        binding.recyclerView.setHasFixedSize(true)

        binding.recyclerViewtwo.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,true)
        binding.recyclerViewtwo.setHasFixedSize(true)

        init()

        val emailID = FirebaseAuth.getInstance().currentUser!!.uid
        db.collection("user").document(emailID).get().addOnSuccessListener {
            if (it != null){
                val email = it.data?.get("email")?.toString()
                binding.username.text = email
            }
        }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }

        imageIdnews = arrayOf(
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4
        )

        imageIdbest = arrayOf(
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4
        )

//        nameList = arrayOf(
//            "Irakli",
//            "Vano",
//            "Shako",
//            "Saba",
//            "Irakli",
//            "Vano",
//            "Shako",
//            "Saba"
//        )
//
//        priceList = arrayOf(
//            "16$",
//            "17$",
//            "3$",
//            "5$",
//            "16$",
//            "17$",
//            "3$",
//            "5$"
//        )



        bestData = arrayListOf<BestData>()
        newsData = arrayListOf<NewsData>()
        getBest()
        getNews()

//        binding.shop.setOnClickListener{
//            binding.shop.visibility = View.GONE
//            binding.shopDark.visibility = View.VISIBLE
//        }
//
//        binding.shopDark.setOnClickListener{
//            binding.shop.visibility = View.VISIBLE
//            binding.shopDark.visibility = View.GONE
//        }
//
//        binding.website.setOnClickListener{
//            binding.website.visibility = View.GONE
//            binding.websiteDark.visibility = View.VISIBLE
//        }
//
//        binding.websiteDark.setOnClickListener{
//            binding.website.visibility = View.VISIBLE
//            binding.websiteDark.visibility = View.GONE
//        }
//
//        binding.android.setOnClickListener{
//            binding.android.visibility = View.GONE
//            binding.androidDark.visibility = View.VISIBLE
//        }
//
//        binding.androidDark.setOnClickListener{
//            binding.android.visibility = View.VISIBLE
//            binding.androidDark.visibility = View.GONE
//        }
//
//        binding.ios.setOnClickListener{
//            binding.ios.visibility = View.GONE
//            binding.iosDark.visibility = View.VISIBLE
//        }
//
//        binding.iosDark.setOnClickListener{
//            binding.ios.visibility = View.VISIBLE
//            binding.iosDark.visibility = View.GONE
//        }
//
//        binding.figma.setOnClickListener{
//            binding.figma.visibility = View.GONE
//            binding.figmaDark.visibility = View.VISIBLE
//        }
//
//        binding.figmaDark.setOnClickListener{
//            binding.figma.visibility = View.VISIBLE
//            binding.figmaDark.visibility = View.GONE
//        }
//
//        binding.xd.setOnClickListener{
//            binding.xd.visibility = View.GONE
//            binding.xdDark.visibility = View.VISIBLE
//        }
//
//        binding.xdDark.setOnClickListener{
//            binding.xd.visibility = View.VISIBLE
//            binding.xdDark.visibility = View.GONE
//        }
//
//        binding.shopDark.setOnClickListener{
//            binding.shop.visibility = View.VISIBLE
//            binding.shopDark.visibility = View.GONE
//        }
//
//        binding.uiux.setOnClickListener{
//            binding.uiux.visibility = View.GONE
//            binding.uiuxDark.visibility = View.VISIBLE
//        }
//
//        binding.uiuxDark.setOnClickListener{
//            binding.uiux.visibility = View.VISIBLE
//            binding.uiuxDark.visibility = View.GONE
//        }
//
//        binding.tv.setOnClickListener{
//            binding.tv.visibility = View.GONE
//            binding.tvDark.visibility = View.VISIBLE
//        }
//
//        binding.tvDark.setOnClickListener{
//            binding.tv.visibility = View.VISIBLE
//            binding.tvDark.visibility = View.GONE
//        }
//
//        binding.shop.setOnClickListener{
//            binding.shop.visibility = View.GONE
//            binding.shopDark.visibility = View.VISIBLE
//        }
//
//        binding.shopDark.setOnClickListener{
//            binding.shop.visibility = View.VISIBLE
//            binding.shopDark.visibility = View.GONE
//        }
//
//        binding.shop.setOnClickListener{
//            binding.shop.visibility = View.GONE
//            binding.shopDark.visibility = View.VISIBLE
//        }
//
//        binding.shopDark.setOnClickListener{
//            binding.shop.visibility = View.VISIBLE
//            binding.shopDark.visibility = View.GONE
//        }
//
//        binding.shop.setOnClickListener{
//            binding.shop.visibility = View.GONE
//            binding.shopDark.visibility = View.VISIBLE
//        }
//
//        binding.shopDark.setOnClickListener{
//            binding.shop.visibility = View.VISIBLE
//            binding.shopDark.visibility = View.GONE
//        }
//
//        binding.shop.setOnClickListener{
//            binding.shop.visibility = View.GONE
//            binding.shopDark.visibility = View.VISIBLE
//        }
//
//        binding.shopDark.setOnClickListener{
//            binding.shop.visibility = View.VISIBLE
//            binding.shopDark.visibility = View.GONE
//        }
//
//        binding.shop.setOnClickListener{
//            binding.shop.visibility = View.GONE
//            binding.shopDark.visibility = View.VISIBLE
//        }
//
//        binding.shopDark.setOnClickListener{
//            binding.shop.visibility = View.VISIBLE
//            binding.shopDark.visibility = View.GONE
//        }
//
//        binding.shop.setOnClickListener{
//            binding.shop.visibility = View.GONE
//            binding.shopDark.visibility = View.VISIBLE
//        }
//
//        binding.shopDark.setOnClickListener{
//            binding.shop.visibility = View.VISIBLE
//            binding.shopDark.visibility = View.GONE
//        }
//
//        binding.shop.setOnClickListener{
//            binding.shop.visibility = View.GONE
//            binding.shopDark.visibility = View.VISIBLE
//        }
//
//        binding.shopDark.setOnClickListener{
//            binding.shop.visibility = View.VISIBLE
//            binding.shopDark.visibility = View.GONE
//        }


    }

    private fun init(){
        binding.apply {
            db2.child(auth.currentUser!!.uid).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userInfo = snapshot.getValue(User::class.java) ?: return
                    binding.username.text = userInfo.email
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "hello", Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    private fun getNews(){
        for (i in imageIdnews.indices){
            val news = NewsData(imageIdnews[i])
            newsData.add(news)
        }
        val adapter = NewsAdapter(newsData)
        binding.recyclerViewtwo.adapter = adapter

        adapter.setOnItemClickListener(object : NewsAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                activity?.let{
                    val intent = Intent (it, ActivityProductPage::class.java)
                    it.startActivity(intent)
                    intent.putExtra("ImageIdnews", newsData[position].image)
                    startActivity(intent)
                }
            }
        })
    }

    private fun getBest(){
        for (i in imageIdbest.indices){
            val best = BestData(imageIdbest[i]) //, nameList[i], priceList[i]
            bestData.add(best)
        }
        val bests = BestAdapter(bestData)
        binding.recyclerView.adapter = bests

        bests.setOnItemClickListener(object : BestAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                activity?.let{
                    val intent = Intent (it, ActivityProductPage::class.java)
                    it.startActivity(intent)
                    intent.putExtra("ImageIdnews", bestData[position].image)
//                    intent.putExtra("name", bestData[position].name)
//                    intent.putExtra("price", bestData[position].price)

                    startActivity(intent)
                }
            }
        })
    }

}
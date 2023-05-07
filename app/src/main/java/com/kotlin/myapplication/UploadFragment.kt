package com.kotlin.myapplication

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.kotlin.myapplication.databinding.FragmentUploadBinding

class UploadFragment : Fragment(R.layout.fragment_upload) {
    private lateinit var binding: FragmentUploadBinding
    var storageRef = Firebase.storage
    private lateinit var databaseReference: DatabaseReference
    private lateinit var uri : Uri
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUploadBinding.bind(view)

        storageRef = FirebaseStorage.getInstance()

        val imageId = FirebaseAuth.getInstance().currentUser!!.uid

        databaseReference = FirebaseDatabase.getInstance().getReference("Images")
            .child(imageId)
        databaseReference.get().addOnSuccessListener {
            val url = it.child("Images").value.toString()

            Glide.with(this).load(url).into(binding.fireimage)
        }
        val galleryImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding.Image.setImageURI(it)
                if (it != null) {
                    uri = it
                }
            })

        binding.browser.setOnClickListener{
            galleryImage.launch("image/*")
        }

        binding.imageUpload.setOnClickListener{
            storageRef.getReference("Images").child((System.currentTimeMillis().toString()))
                .putFile(uri)
                .addOnSuccessListener {
                    val userId = FirebaseAuth.getInstance().currentUser!!.uid
                    val mapImage = mapOf(
                        "url" to it.toString()
                    )

                    val databaseReference =
                        FirebaseDatabase.getInstance().getReference("userImages")
                    databaseReference.child(userId).setValue(mapImage)
                        .addOnSuccessListener {
                            Log.d("msg", "succes")
                        }
                        .addOnFailureListener{ error ->
                            Log.d("msg", "failed")
                        }

                }
        }

    }
}
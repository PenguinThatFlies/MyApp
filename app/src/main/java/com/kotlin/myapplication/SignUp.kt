package com.kotlin.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kotlin.myapplication.databinding.ActivitySignUpBinding

class SignUp:AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        signupListeners()

        emailFocus()
        PasswordFocus()

        binding.pageSignUp.setOnClickListener{
            startActivity(Intent(this,SignIn::class.java))
            finish()
        }


    }
    // submitForm
    private fun submitForm(){
        binding.EmailInputLayout.helperText = validEmail()
        binding.PasswordInputLayout.helperText = validPassword()

        val validEmail = binding.EmailInputLayout.helperText == null
        val validPassword = binding.PasswordInputLayout.helperText == null

        if (validEmail && validPassword)
            resetForm()
        else
            invalidForm()
    }

//incalidForm
    private fun invalidForm(){
        var message = ""
        if(binding.EmailInputLayout.helperText != null)
            message += "\n\nEmail: " + binding.EmailInputLayout.helperText
        if(binding.PasswordInputLayout.helperText != null)
            message += "\n\nPassword: " + binding.PasswordInputLayout.helperText

        AlertDialog.Builder(this)
            .setTitle("Invalid Form")
            .setMessage(message)
            .setPositiveButton("Okay"){_,_ ->

            }
    }
// ResetForm
    private fun resetForm()
    {
        var message = "Email: " + binding.edEmail.text
        message += "\nPassword: " + binding.edPassword.text
        AlertDialog.Builder(this)
            .setTitle("Form submitted")
            .setMessage(message)
            .setPositiveButton("Okay"){ _,_ ->
                binding.edEmail.text = null
                binding.edPassword.text = null

                binding.EmailInputLayout.helperText = getString(R.string.required)
                binding.PasswordInputLayout.helperText = getString(R.string.required)
            }
            .show()
    }

    //Email Invalid
    private fun emailFocus(){
        binding.edEmail.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus){
                binding.EmailInputLayout.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val emailText = binding.edEmail.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            return "Invalid Email Address"
        }
        return null
    }
    //Password Invalid
    private fun PasswordFocus(){
        binding.edPassword.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus){
                binding.PasswordInputLayout.helperText = validPassword()
            }
        }
    }
//validPassword
    private fun validPassword(): String? {
        val emailText = binding.edPassword.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            return "Invalid Passowrd"
        }
        return null
    }

    private fun signupListeners() {
        binding.submitButton2.setOnClickListener {
            val email = binding.edEmail.text.toString().trim()
            val pass = binding.edPassword.text.toString().trim()
            val confirmPass = binding.edConfPassword.text.toString().trim()




            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {

                            val user = hashMapOf(
                                "email" to email,
                                "password" to pass
                            )

                            val userId = FirebaseAuth.getInstance().currentUser!!.uid

                            db.collection("user").document(userId).set(user).addOnSuccessListener {
                                binding.edEmail.text?.clear()
                                binding.edPassword.text?.clear()

                            }


                            val intent = Intent(this, SignIn::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
    }

}
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
import com.google.firebase.ktx.Firebase
import com.kotlin.myapplication.databinding.ActivitySignInBinding

class SignIn: AppCompatActivity() {
    lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        emailFocus()
        PasswordFocus()

        binding.pageSignUp.setOnClickListener{
            startActivity(Intent(this,SignUp::class.java))
            finish()
        }

        binding.submitButton.setOnClickListener {
            submitForm()
            loginListeners()
        }


    }

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

    private fun validPassword(): String? {
        val emailText = binding.edPassword.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            return "Invalid Passowrd"
        }
        return null
    }


    private fun loginListeners() {
        binding.submitButton.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val pass = binding.edPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onStart() {
        super.onStart()

        if(firebaseAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
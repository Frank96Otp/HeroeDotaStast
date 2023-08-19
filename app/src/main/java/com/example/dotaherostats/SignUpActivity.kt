package com.example.dotaherostats

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.dotaherostats.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var  binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //intancia de firebaseAuth
        firebaseAuth = Firebase.auth

        //valida los campos email y password para activar el botton register
        binding.etEmail.addTextChangedListener {
            binding.btnLogin.isEnabled = validateEmailPassword(it.toString() , binding.etPassword.text.toString())
        }
        binding.etPassword.addTextChangedListener {
            binding.btnLogin.isEnabled = validateEmailPassword(binding.etEmail.text.toString() , it.toString())
        }



        binding.btnLogin.setOnClickListener {
                firebaseAuth.createUserWithEmailAndPassword(binding.etEmail.text.toString(),binding.etPassword.text.toString())
                    .addOnCompleteListener(this) {
                        if(it.isSuccessful){
                            Toast.makeText(this, "EL USUARIO A SIDO CREADO",Toast.LENGTH_LONG).show()
                            goToLogin()
                        }else{
                            Toast.makeText(this, "!!!!ERROR AL CREAR EL USUARIO",Toast.LENGTH_LONG).show()
                        }
                    }
        }
    }

    private fun validateEmailPassword(email: String, password: String):Boolean {
        val validateEmail = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val validatePassowod = password.length >= 8
        return validateEmail && validatePassowod
    }

    private fun goToLogin() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }

}
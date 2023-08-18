package com.example.dotaherostats

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import com.example.dotaherostats.databinding.ActivityLoginBinding
import com.example.dotaherostats.onboarding.Onbording
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharePreferences: SharedPreferences
    private lateinit var googleLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = Firebase.auth

        googleLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    try {
                        val account = task.getResult(ApiException::class.java)
                        authFireBAseWithGoogle(account.idToken)
                    } catch (e: Exception) {
                    }
                }
            }

        sharePreferences = this.getSharedPreferences(SESSION_PREFERENS_KEY, Context.MODE_PRIVATE)
        val email = sharePreferences.getString(EMAIL_DATA, "")

        if (email != null) {
            if (email.isNotEmpty()) {
                goToMenu()
            }
        }

        setViews()
    }



    private fun setViews() {

        binding.etEmail.addTextChangedListener {
            binding.btnLogin.isEnabled = validateEmailPassword(it.toString() , binding.etPassword.text.toString())
        }
        binding.etPassword.addTextChangedListener {
            binding.btnLogin.isEnabled = validateEmailPassword(binding.etEmail.text.toString() , it.toString())
        }


        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            signInWithFirebas(email, password)
        }

        binding.btnLoginWithGoogle.setOnClickListener {
            signinWithGoogle()
        }

        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            signUpWithFirebase(email, password)

        }
    }


    private fun authFireBAseWithGoogle(idToken: String?) {
        var authCredential = GoogleAuthProvider.getCredential(idToken!!, null)
        firebaseAuth.signInWithCredential(authCredential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    with(sharePreferences.edit()) {
                        putString(EMAIL_DATA, user?.email).commit()
                    }
                    goToMenu()
                } else {
                    Toast.makeText(this, "Ocurrio un error", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun signinWithGoogle() {
        TODO("Not yet implemented")
    }

    private fun signInWithFirebas(email: String, password: String) {
        TODO("Not yet implemented")
    }

    private fun signUpWithFirebase(email: String, password: String) {
        TODO("Not yet implemented")
    }


    private fun goToMenu() {
        val intent = Intent(this, Onbording::class.java)
        startActivity(intent)
        finish()
    }
    private fun validateEmailPassword(email: String, password: String):Boolean {
        val validateEmail = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val validatePassowod = password.length >= 8
        return validateEmail && validatePassowod
    }
    companion object {
        const val SESSION_PREFERENS_KEY = "SESSION_PREFERENS_KEY"
        const val EMAIL_DATA = "EMAIL_DATA"
    }
}
package com.example.dotaherostats

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import com.example.dotaherostats.databinding.ActivityLoginBinding
import com.example.dotaherostats.onboarding.Onbording
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
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
                        Toast.makeText(this,"error en el lanuncher desalida", Toast.LENGTH_LONG).show()
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

            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)

        }
    }


    private fun authFireBAseWithGoogle(idToken: String?) {
        Log.i("idToken", idToken!!)
        var authCredential = GoogleAuthProvider.getCredential(idToken!!, null)
        firebaseAuth.signInWithCredential(authCredential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    goToMenu()
                    with(sharePreferences.edit()) {
                        putString(EMAIL_DATA, user?.email).commit()
                    }

                } else {
                    Toast.makeText(this, "Ocurrio un error", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun signinWithGoogle() {
        val googleSingInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.key_google))//validadar token
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this, googleSingInOptions)
        val intent = googleClient.signInIntent
        googleLauncher.launch(intent)
    }

    private fun signInWithFirebas(email: String, password: String) {
            if(validateEmailPassword(email,password)){
                    firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(this) {
                            if(it.isSuccessful){

                                goToMenu()
                            }else{
                                Toast.makeText(this,"Email o Password incorrectos",Toast.LENGTH_LONG).show()
                            }
                        }
            }
    }




    private fun goToMenu() {
        val intent = Intent(this, BottonNavigationMain::class.java)
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
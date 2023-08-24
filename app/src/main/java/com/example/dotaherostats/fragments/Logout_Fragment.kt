package com.example.dotaherostats.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dotaherostats.Login
import com.example.dotaherostats.R
import com.example.dotaherostats.databinding.FragmentLogoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class Logout_Fragment : Fragment() {

    private lateinit var  binding: FragmentLogoutBinding
    private lateinit var  sharedPreferences: SharedPreferences
    private lateinit var  firebaseAuth: FirebaseAuth
    private lateinit var email:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences(Login.SESSION_PREFERENS_KEY,
            Context.MODE_PRIVATE)

        email =  sharedPreferences.getString(Login.EMAIL_DATA, "")?:""
        firebaseAuth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        binding = FragmentLogoutBinding.inflate(layoutInflater , container ,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvEmail.text = email
        binding.btnLogout.setOnClickListener {
            with(sharedPreferences.edit()){
                putString(Login.EMAIL_DATA ,"").apply()
            }

            firebaseAuth.signOut()

            goToLogin()
        }
    }

    private fun goToLogin() {
        val intent = Intent(requireActivity(), Login::class.java)
        requireActivity().startActivity(intent)
        requireActivity().finish()
    }


}
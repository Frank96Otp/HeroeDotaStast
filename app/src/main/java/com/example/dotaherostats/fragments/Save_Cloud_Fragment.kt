package com.example.dotaherostats.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dotaherostats.R
import com.example.dotaherostats.databinding.FragmentSaveCloudBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Save_Cloud_Fragment : Fragment() {

    private  lateinit var firestor:  FirebaseFirestore
    private lateinit var  binding:  FragmentSaveCloudBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firestor =  Firebase.firestore
        binding = FragmentSaveCloudBinding.inflate(layoutInflater , container ,false)




        binding.btnRegisterProduct.setOnClickListener {

            val name_heroe =  binding.tilNameHero.editText?.text.toString()
            val main_rol_heroe =  binding.tilMainRol.editText?.text.toString()
            val base_int =  binding.tilBaseInt.editText?.text.toString()
            val base_agi =  binding.tilBaseAgi.editText?.text.toString()
            val base_str =  binding.tilBaseStr.editText?.text.toString()

            if(name_heroe.isNotEmpty() && main_rol_heroe.isNotEmpty() && base_int.isNotEmpty() && base_agi.isNotEmpty() && base_str.isNotEmpty()){
                addHeroeFireStore(name_heroe,main_rol_heroe , base_int.toDouble() , base_agi.toDouble() , base_str.toDouble())
            }
        }

        return binding.root
    }

    private fun addHeroeFireStore(nameHeroe: String, mainRolHeroe: String, base_int: Double, base_agi: Double, base_str: Double) {

        val heroe =  hashMapOf<String, Any>(
            "name_heroe" to nameHeroe,
            "main_rol_heroe" to mainRolHeroe,
            "base_int" to base_int,
            "base_agi" to base_agi,
            "base_str" to base_str
        )

        firestor.collection("heroes").add(heroe).
                addOnSuccessListener {
                    Toast.makeText(binding.btnRegisterProduct.context ,   "Producto Agregado: ${it.id}"  ,Toast.LENGTH_LONG ).show()
                }
                .addOnFailureListener {
                    Toast.makeText(binding.btnRegisterProduct.context ,   "No fue agregado el producto"  ,Toast.LENGTH_LONG ).show()
                }
    }


}
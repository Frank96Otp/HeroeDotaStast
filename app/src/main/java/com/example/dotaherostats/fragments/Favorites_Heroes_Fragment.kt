package com.example.dotaherostats.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dotaherostats.R
import com.example.dotaherostats.databinding.FragmentFavoritesHeroesBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Favorites_Heroes_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Favorites_Heroes_Fragment : Fragment() {

    private lateinit var binding : FragmentFavoritesHeroesBinding
    private  lateinit var firestor: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        firestor =  Firebase.firestore
        binding = FragmentFavoritesHeroesBinding.inflate(layoutInflater , container ,false)
        // Inflate the layout for this fragment

        firestor.collection("heroes").get()
            .addOnSuccessListener {
                val heroes = it.documents
                for(heroe in heroes){
                    Log.i("Heroes", heroe["name_heroe"].toString())
                    Log.i("Heroes", heroe["main_rol_heroe"].toString())
                    Log.i("Heroes", heroe["base_agi"].toString())
                    Log.i("Heroes", heroe["base_int"].toString())
                    Log.i("Heroes", heroe["base_str"].toString())
                }
            }


        return binding.root
    }


}



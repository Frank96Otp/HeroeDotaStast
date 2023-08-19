package com.example.dotaherostats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.dotaherostats.databinding.ActivityBottonNavigationMainBinding

class BottonNavigationMain : AppCompatActivity() {

    private lateinit var binding: ActivityBottonNavigationMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottonNavigationMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        //configurando el fragment conteiner view
        val fragmentView = supportFragmentManager.findFragmentById(R.id.fcv_navigation)as NavHostFragment

        val navController = fragmentView.navController


        binding.bnvMain.setupWithNavController(navController)
    }

}
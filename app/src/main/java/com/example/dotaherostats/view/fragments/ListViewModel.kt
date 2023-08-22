package com.example.dotaherostats.view.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dotaherostats.data.repository.HeroesRepository
import com.example.dotaherostats.data.response.ApiResponse
import com.example.dotaherostats.model.Heroe

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ListViewModel : ViewModel() {
    val repository = HeroesRepository()
    val heroesList: MutableLiveData<List<Heroe>?> = MutableLiveData()

    fun getHeroesFromService() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getHeroes()
            when (response) {
                is ApiResponse.Error -> {
                    println("get fallido")

                    // Mostrar el mensaje de error en la interfaz de usuario (por ejemplo, utilizando LiveData)
                }
                is ApiResponse.Success -> {
                    heroesList.postValue(response.data)
                    println("get exitoso")
                }
            }

        }
    }
}
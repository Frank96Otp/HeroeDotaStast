package com.example.dotaherostats.data.retrofit

import com.example.dotaherostats.model.Heroe
import retrofit2.http.GET

interface HeroeService {
    @GET("f24626ba-c450-4e10-8197-8c57d028b5bb")
    suspend fun getHeroes(): List<Heroe>
}
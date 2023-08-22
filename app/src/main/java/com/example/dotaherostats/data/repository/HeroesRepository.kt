package com.example.dotaherostats.data.repository

import com.example.dotaherostats.data.response.ApiResponse
import com.example.dotaherostats.data.retrofit.ServiceInstance
import com.example.dotaherostats.model.Heroe


class HeroesRepository {
    suspend fun getHeroes(): ApiResponse<List<Heroe>> {
        return try {
            val response = ServiceInstance.getHeroeService().getHeroes()
            ApiResponse.Success(response)
        } catch (e: Exception) {
            ApiResponse.Error(e)
        }
    }
}
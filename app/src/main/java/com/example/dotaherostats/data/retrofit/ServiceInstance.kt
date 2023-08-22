package com.example.dotaherostats.data.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceInstance {
    //https://run.mocky.io/v3/db513fd8-fb5e-4838-ac79-0dfdb0255456
    private  val retrofit = Retrofit.Builder().baseUrl("https://run.mocky.io/v3/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getHeroeService():HeroeService = retrofit.create(HeroeService::class.java)
}
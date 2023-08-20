package com.example.pokedex.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: PokedexApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://16.171.23.212:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokedexApi::class.java)
    }
}
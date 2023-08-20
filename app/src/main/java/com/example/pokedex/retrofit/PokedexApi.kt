package com.example.pokedex.retrofit

import com.example.pokedex.model.Base64Image
import com.example.pokedex.pojo.PokemonName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

interface PokedexApi {

    @POST("predict")
    fun getPokemonName(@Body body: Base64Image): Call<PokemonName>
}
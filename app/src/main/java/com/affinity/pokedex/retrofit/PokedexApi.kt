package com.affinity.pokedex.retrofit

import com.affinity.pokedex.model.Base64Image
import com.affinity.pokedex.pojo.PokemonName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PokedexApi {

    @POST("predict")
    fun getPokemonName(@Body body: Base64Image): Call<PokemonName>
}
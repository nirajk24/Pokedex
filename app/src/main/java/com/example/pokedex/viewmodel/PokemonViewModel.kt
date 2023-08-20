package com.example.pokedex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.pokedex.model.Pokemon

class PokemonViewModel(private val application: Application) : AndroidViewModel(application) {


    private lateinit var pokemon : Pokemon
    fun setCurrentPokemon(pokemon : Pokemon){
        this.pokemon = pokemon
    }
    fun getCurrentPokemon() = pokemon



}
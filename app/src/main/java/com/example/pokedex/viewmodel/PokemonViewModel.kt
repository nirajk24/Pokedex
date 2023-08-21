package com.example.pokedex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.pokedex.model.Pokemon

class PokemonViewModel(private val application: Application) : AndroidViewModel(application) {

    private lateinit var pokemonList : List<Pokemon>
    fun setCurrentPokemonList(pokemonList : List<Pokemon>){
        this.pokemonList = pokemonList
    }
    fun getCurrentPokemonList() = pokemonList



}
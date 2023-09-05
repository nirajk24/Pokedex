package com.affinity.pokedex.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonSmall(
    val id: String,
    val name: String,
    val evolutions: List<String>,
    val imageurl: String,
    val typeofpokemon: List<String>
)
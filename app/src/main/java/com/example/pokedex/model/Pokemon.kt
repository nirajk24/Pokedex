package com.example.pokedex.model

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val id: String,
    val name: String,
    val imageurl: String,

    val xdescription: String,
    val ydescription: String,

    val height: String,
    val category: String,
    val weight: String,

    val typeofpokemon: List<String>,
    val weaknesses: List<String>,
    val evolutions: List<String>,
    val abilities: List<String>,

    val hp: Int,
    val attack: Int,
    val defense: Int,
    val special_attack: Int,
    val special_defense: Int,
    val speed: Int,
    val total: Int,

    val genderless: Int,
    val male_percentage: String = "",
    val female_percentage: String = "",

    val cycles: String,
    val egg_groups: String,

    val evolvedfrom: String,
    val reason: String,

    val base_exp: String,

)
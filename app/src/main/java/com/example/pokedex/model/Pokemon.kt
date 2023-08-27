package com.example.pokedex.model

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val id: String,
    val name: String,
    val imageurl: String,

    val xdescription: String,
    val ydescription: String,
    val description: String,

    val height: String,
    val category: String,
    val weight: String,

    val typeofpokemon: List<String>,
    val weaknesses: List<String>,
    val evolutions: List<String>,
    val abilities: List<String>,
    val abilities_hidden: String,

    val hp: Int,
    val attack: Int,
    val defense: Int,
    val sp_attack: Int,
    val sp_defense: Int,
    val speed: Int,
    val total_points: Int,

    val genderless: Int,
    val percent_male: String,
    val percent_female: String,

    val cycles: String = "??",
    val egg_groups: String = "??",

    val evolvedfrom: String,
    val reason: String,

    val base_exp: String,

    val classification: String,
    val capture_rate: String,
    val base_egg_steps: Int,

    val growth_rate: String,
    val status: String,
    val base_friendship: String = "??",

    val effectiveness: List<Int>

)
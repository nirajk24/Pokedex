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

//    val against_bug: Double,
//    val against_dark: Double,
//    val against_dragon: Double,
//    val against_electric: Double,
//    val against_fairy: Double,
//    val against_fight: Double,
//    val against_fire: Double,
//    val against_flying: Double,
//    val against_ghost: Double,
//    val against_grass: Double,
//    val against_ground: Double,
//    val against_ice: Double,
//    val against_normal: Double,
//    val against_poison: Double,
//    val against_psychic: Double,
//    val against_rock: Double,
//    val against_steel: Double,
//    val against_water: Double,



)
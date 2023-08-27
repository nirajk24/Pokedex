package com.example.pokedex.repository

import android.content.Context
import android.preference.PreferenceManager

class MyPreferences(context: Context?) {

    companion object {
        private const val DARK_STATUS = "DARK_STATUS"
        private const val GRID_STATUS = "GRID_STATUS"
        private const val USERNAME = "USERNAME"
        private const val IS_CHANGED = "IS_CHANGED"

        private const val POKEMON_COLLECTED = "POKEMON_COLLECTED"

        private const val CURRENT_AVATAR = "CURRENT_AVATAR"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var darkMode = preferences.getInt(DARK_STATUS, 2)
        set(value) = preferences.edit().putInt(DARK_STATUS, value).apply()

    var isLinear = preferences.getBoolean(GRID_STATUS, true)
        set(value) = preferences.edit().putBoolean(GRID_STATUS, value).apply()

    var username = preferences.getString(USERNAME, "Set Username")
        set(value) = preferences.edit().putString(USERNAME, value).apply()

    var isChanged = preferences.getBoolean(IS_CHANGED, false)
        set(value) = preferences.edit().putBoolean(IS_CHANGED, value).apply()


    var collectedPokemons: Set<Int>
        get() {
            val str = preferences.getString(POKEMON_COLLECTED, "")
            val intList = str?.split(",")?.mapNotNull { it.toIntOrNull() }
            return intList?.toSet() ?: emptySet()
        }
        set(value) {
            val intSet = collectedPokemons.toMutableSet() // Create a mutable copy
            intSet.addAll(value) // Add the new Pokémon IDs
            val intString = intSet.joinToString(",")
            preferences.edit().putString(POKEMON_COLLECTED, intString).apply()
        }

    fun addCollectedPokemon(pokemonId: Int) {
        collectedPokemons = setOf(pokemonId)
    }


    val collectedPokemonCount: Int
        get() = collectedPokemons.size


    var currentAvatar = preferences.getInt(CURRENT_AVATAR, 0)
        set(value) = preferences.edit().putInt(CURRENT_AVATAR, value).apply()
}
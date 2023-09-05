package com.affinity.pokedex.utility


object ColorUtils {
    private val colorMap = mapOf(
        "Normal" to "A8A77A",
        "Fire" to "EE8130",
        "Water" to "539AE2",
        "Electric" to "EAC733",
        "Grass" to "71C558",
        "Ice" to "96D9D6",
        "Fighting" to "C22E28",
        "Poison" to "A33EA1",
        "Ground" to "E2BF65",
        "Flying" to "A98FF3",
        "Psychic" to "F95587",
        "Bug" to "A6B91A",
        "Rock" to "B6A136",
        "Ghost" to "735797",
        "Dragon" to "6F35FC",
        "Dark" to "705746",
        "Steel" to "B7B7CE",
        "Fairy" to "D685AD"

    )
    fun getColorForString(str: String): String {
        return colorMap[str] ?: "FFFFFF"
    }

    fun getAllKeys(): List<String>{
        return colorMap.keys.toList()
    }


}
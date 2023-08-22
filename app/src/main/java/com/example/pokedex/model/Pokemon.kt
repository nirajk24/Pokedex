package com.example.pokedex.model

import android.content.Context
import kotlinx.serialization.Serializable

import com.opencsv.CSVReaderBuilder
import com.opencsv.CSVParserBuilder
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.LineNumberReader


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

fun readCsvLineByIndex(context: Context, resourceId: Int, indices: List<Int>): List<Pokemon> {
    val resultList = mutableListOf<Pokemon>()

    for (index in indices) {
        val inputStream = context.resources.openRawResource(resourceId)
        val reader = CSVReaderBuilder(InputStreamReader(inputStream))
            .withCSVParser(CSVParserBuilder().withSeparator(',').build())
            .build()

        var lineNumber = 0
        var line: Array<String>?

        while (reader.readNext().also { line = it } != null) {
            if (lineNumber == index) {
                val pokemon = parseCsvLine(line!!)
                resultList.add(pokemon)
                break
            }
            lineNumber++
        }

        reader.close()
    }

    return resultList
}




//fun readCsvLineByIndex(context: Context, resourceId: Int, indices: List<Int>): List<Pokemon> {
//    val inputStream = context.resources.openRawResource(resourceId)
//    val lines = inputStream.bufferedReader().readLines()
//
//    val resultList = mutableListOf<Pokemon>()
//
//    for (index in indices) {
//        if (index < lines.size) {
//            val lineArray = lines[index].split(",")
//            val pokemon = parseCsvLine(lineArray.toTypedArray())
//            resultList.add(pokemon)
//        }
//    }
//
//    return resultList
//}


fun parseCsvLine(line: Array<String>): Pokemon {
    return Pokemon(
        id = line[1],
        name = line[0],
        imageurl = line[2],
        xdescription = line[3],
        ydescription = line[4],
        height = line[5],
        category = line[6],
        weight = line[7],
        typeofpokemon = line[8].removeSurrounding("[", "]").split(", ").map { it.replace("'", "") },
        weaknesses = line[9].removeSurrounding("[", "]").split(", ").map { it.replace("'", "") },
        evolutions = line[10].removeSurrounding("[", "]").split(", ").map { it.replace("'", "") },
        abilities = line[11].removeSurrounding("[", "]").split(", ").map { it.replace("'", "") },
        genderless = line[12].toIntOrNull() ?: 0,
        cycles = line[13],
        egg_groups = line[14],
        evolvedfrom = line[15],
        reason = line[16],
        base_exp = line[17],
        classification = line[18],
        percent_male = line[19],
        percent_female = line[20],
        abilities_hidden = line[21],
        capture_rate = line[22],
        base_egg_steps = line[23].toIntOrNull() ?: 0,
        hp = line[24].toIntOrNull() ?: 0,
        attack = line[25].toIntOrNull() ?: 0,
        defense = line[26].toIntOrNull() ?: 0,
        sp_attack = line[27].toIntOrNull() ?: 0,
        sp_defense = line[28].toIntOrNull() ?: 0,
        speed = line[29].toIntOrNull() ?: 0,
        description = line[30],
        status = line[31],
        total_points = line[32].toIntOrNull() ?: 0,
        base_friendship = line[33],
        growth_rate = line[34],
        effectiveness = line[35].removeSurrounding("[", "]").split(", ").map { it.toIntOrNull() ?: 0 }
    )
}



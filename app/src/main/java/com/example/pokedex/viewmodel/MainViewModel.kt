package com.example.pokedex.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.pokedex.R
import com.example.pokedex.model.Base64Image
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonSmall
import com.example.pokedex.model.readCsvLineByIndex
import com.example.pokedex.pojo.PokemonName
import com.example.pokedex.repository.Repository
import com.example.pokedex.retrofit.RetrofitInstance
import com.example.pokedex.utility.NameToId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.InputStream

class MainViewModel(
    private val repository: Repository,
    private val application: Application
) : AndroidViewModel(application) {


    lateinit var onApiResult : ((PokemonSmall, Double) -> Unit)

    private var pokemonList = MutableLiveData<List<PokemonSmall>>()
    fun observePokemonListLiveData() = pokemonList


    fun initializePokemon(inputStream : InputStream){
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val json = Json { ignoreUnknownKeys = true }
        val pokemonArray: Array<PokemonSmall> = json.decodeFromString(jsonString)
        pokemonList.value = pokemonArray.toList()
    }


    fun fetchPokemonFromApi(raw_data : String){
        val base64Image = Base64Image(raw_data)

        RetrofitInstance.api.getPokemonName(base64Image).enqueue(object: Callback<PokemonName>{
            override fun onResponse(call: Call<PokemonName>, response: Response<PokemonName>) {
//                Log.d("API", response.toString())
                if(response.code() != 500 && response.body() != null){
                    val pokemonName: PokemonName = response.body()!!

                    val text = pokemonName.class_name.toString() + "\n" + pokemonName.prob
                    val id = NameToId.nameToIdMap[pokemonName.class_name]
//                    Log.d("API", text)
//                    Log.d("API", id.toString())
                    onApiResult(getPokemonById(id!!), pokemonName.prob.toDouble())

//                    Log.d("API", text)

//                    binding.tvBase64.text = text
                } else {
//                    Log.d("API", "RESPONSE BODY NULL")

                }
            }

            override fun onFailure(call: Call<PokemonName>, t: Throwable) {
                // Log will give us the reason for Failure through t.message
//                Log.d("API", t.message.toString())
            }
        })
    }

    fun getPokemonById(pokemonId : String) : PokemonSmall{
        val pokemon = observePokemonListLiveData()
            .value?.get(getIdFromString(pokemonId) - 1)
        return pokemon!!
    }

    fun getPokemonEvolutionList(pokemon: PokemonSmall): List<Pokemon> {
        // Read csv and get list
        val specificLineIndices = getEvolutionIds(pokemon)

        return readCsvLineByIndex(application, R.raw.pokemon_csv,
            specificLineIndices.toMutableList())
    }

    private fun getEvolutionIds(pokemon: PokemonSmall) : List<Int>{
        val pokemonEvolutionIds = mutableListOf<Int>()
        pokemonEvolutionIds.add(getIdFromString(pokemon.id))
        for(id in pokemon.evolutions){
            val idInt = getIdFromString(id)
            pokemonEvolutionIds.add(idInt)
        }
//        Log.d("CHECK", pokemonEvolutionIds.toString())
        return pokemonEvolutionIds
    }
    fun getIdFromString(id: String): Int {
        var idInt = 0
        val length = id.length
        for(i in 1 until length){
            idInt = (idInt * 10) + id[i].toString().toInt()
        }

        return idInt
    }

    fun filterPokemons(searchQuery: String): List<PokemonSmall> {
        val pokemonList = observePokemonListLiveData().value
        if (pokemonList != null) {
            if (searchQuery.isNotEmpty()) {
                return pokemonList.filter { pokemon ->
                    pokemon.name.contains(searchQuery, ignoreCase = true) ||
                            pokemon.id.contains(searchQuery, ignoreCase = true) ||
                            pokemon.typeofpokemon.any { type ->
                                type.contains(
                                    searchQuery,
                                    ignoreCase = true
                                ) } }
            }  else {
                return pokemonList
            }
        }
        return emptyList()
    }



    // Converting Image to Byte64 String format
    fun convertToByte64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()

        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }


}
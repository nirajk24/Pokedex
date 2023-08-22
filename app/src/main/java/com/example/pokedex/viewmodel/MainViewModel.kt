package com.example.pokedex.viewmodel

import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokedex.R
import com.example.pokedex.model.Base64Image
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonSmall
import com.example.pokedex.model.readCsvLineByIndex
import com.example.pokedex.pojo.PokemonName
import com.example.pokedex.repository.Repository
import com.example.pokedex.retrofit.RetrofitInstance
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


    private var pokemonList = MutableLiveData<List<PokemonSmall>>()
    fun observePokemonListLiveData() = pokemonList

    var transitionName: String? = null
    
    private var isLogin = false
    fun isLogin() = isLogin

    fun initializePokemon(inputStream : InputStream){
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val json = Json { ignoreUnknownKeys = true }
        val pokemonArray: Array<PokemonSmall> = json.decodeFromString(jsonString)
        pokemonList.value = pokemonArray.toList()
    }




    // Converting Image to Byte64 String format
    fun convertToByte64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()

        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }


    fun getPokemonNameByImage(raw_data : String){
        val base64Image = Base64Image(raw_data)

        RetrofitInstance.api.getPokemonName(base64Image).enqueue(object: Callback<PokemonName>{
            override fun onResponse(call: Call<PokemonName>, response: Response<PokemonName>) {
                if(response.code() != 500 && response.body() != null){
                    val pokemonName: PokemonName = response.body()!!

                    val text = pokemonName.class_name.toString() + "\n" + pokemonName.prob
//                    binding.tvBase64.text = text
                }
            }

            override fun onFailure(call: Call<PokemonName>, t: Throwable) {
                // Log will give us the reason for Failure through t.message
                Log.d("TEST", t.message.toString())
            }
        })
    }

    fun getPokemonEvolutionList(pokemon: PokemonSmall): List<Pokemon> {
        // Read csv and get list
        val specificLineIndices = getEvolutionIds(pokemon)

        return readCsvLineByIndex(application, R.raw.pokemon_csv, specificLineIndices.toMutableList())
    }

    private fun getEvolutionIds(pokemon: PokemonSmall) : List<Int>{
        val pokemonEvolutionIds = mutableListOf<Int>()
        pokemonEvolutionIds.add(getIdFromString(pokemon.id))
        for(id in pokemon.evolutions){
            val idInt = getIdFromString(id)
            pokemonEvolutionIds.add(idInt)
        }
        Log.d("CHECK", pokemonEvolutionIds.toString())
        return pokemonEvolutionIds
    }
    private fun getIdFromString(id: String): Int {
        var idInt = 0
        val length = id.length
        for(i in 1 until length){
            idInt = (idInt * 10) + id[i].toString().toInt()
        }

        return idInt
    }



}
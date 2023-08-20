package com.example.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.pokedex.databinding.ActivityPokemonBinding
import com.example.pokedex.model.Pokemon
import com.example.pokedex.repository.Repository
import com.example.pokedex.viewmodel.MainViewModel
import com.example.pokedex.viewmodel.MainViewModelFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class PokemonActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPokemonBinding

    private lateinit var pokemon : Pokemon


    private val mainMvvm : MainViewModel by lazy{
        val repository = Repository()
        val mainViewModelFactory = MainViewModelFactory(repository, application)
        ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        val transitionName = extras?.getString("TRANSITION_NAME")
        val data = extras?.getString("POKEMON")
        val pokemon = Json.decodeFromString<Pokemon>(data!!)
        ViewCompat.setTransitionName(binding.targetImageView, transitionName)

        // Start the shared element transition
        supportPostponeEnterTransition()
        binding.targetImageView.doOnPreDraw {
            supportStartPostponedEnterTransition()
        }
//        binding.targetImageView.transitionName = transitionName

        val transition = TransitionInflater.from(this).inflateTransition(android.R.transition.move)
        window.sharedElementReturnTransition = transition


        Glide.with(binding.targetImageView)
            .load(pokemon.imageurl)
            .into(binding.targetImageView)
    }
}
package com.example.pokedex

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.pokedex.adapter.ViewPagerAdapter
import com.example.pokedex.databinding.ActivityPokemonBinding
import com.example.pokedex.model.Pokemon
import com.example.pokedex.repository.Repository
import com.example.pokedex.utility.ColorUtils
import com.example.pokedex.utility.TypeUtils
import com.example.pokedex.viewmodel.MainViewModel
import com.example.pokedex.viewmodel.MainViewModelFactory
import com.example.pokedex.viewmodel.PokemonViewModel
import com.example.pokedex.viewmodel.PokemonViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class PokemonActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPokemonBinding

    private lateinit var pokemon : Pokemon

    private val tabsArray = arrayOf("About", "Stats", "Evolution")

    private val pokemonMvvm : PokemonViewModel by lazy{
        val pokemonViewModelFactory = PokemonViewModelFactory(application)
        ViewModelProvider(this, pokemonViewModelFactory)[PokemonViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setBackButton()

        setImageTransitionAnim()
        initializeViewPager()

        initializeTopImage()




    }

    private fun initializeTopImage() {

        val pokemonType1 = pokemon.typeofpokemon[0]
        val color = ColorUtils.getColorForString(pokemonType1)
        val pokemonType1Icon = TypeUtils.typeMap[pokemonType1]

        binding.apply {
            tvPokemonName.text = pokemon.name
            tvPokemonName2.text = pokemon.name
            tvPokemonId.text = pokemon.id

            mainBackground.setBackgroundColor(Color.parseColor("#DA$color"))

            // Setting 1st Type Card
            tvPokemonType1.text = pokemonType1
            cvPokemonType1.background.setTint(Color.parseColor("#$color"))
        }

        Glide.with(binding.root)
            .load(pokemonType1Icon)
            .into(binding.ivPokemonType1)

        // Setting 2nd Type Card
        if(pokemon.typeofpokemon.size > 1){
            binding.cvPokemonType2.visibility = View.VISIBLE

            val pokemonType2 = pokemon.typeofpokemon[1]
            binding.tvPokemonType2.text = pokemonType2

            val color2 = ColorUtils.getColorForString(pokemonType2)
            binding.cvPokemonType2.background.setTint(Color.parseColor("#$color2"))

            val pokemonType2Icon = TypeUtils.typeMap[pokemonType2]
//                ivPokemonType2.setImageResource(pokemonType2Icon!!)
            Glide.with(binding.root)
                .load(pokemonType2Icon)
                .into(binding.ivPokemonType2)
        }

        binding.tabLayout.apply {
            setBackgroundColor(Color.parseColor("#00$color"))
            setSelectedTabIndicatorColor(Color.parseColor("#$color"))
        }
    }

    private fun setImageTransitionAnim() {
        val extras = intent.extras
        val transitionName = extras?.getString("TRANSITION_NAME")
        val data = extras?.getString("POKEMON")
        pokemon = Json.decodeFromString<Pokemon>(data!!)
        pokemonMvvm.setCurrentPokemon(pokemon)
        ViewCompat.setTransitionName(binding.ivPokemonImage, transitionName)

        // Start the shared element transition
        supportPostponeEnterTransition()
        binding.ivPokemonImage.doOnPreDraw {
            supportStartPostponedEnterTransition()
        }
//        binding.targetImageView.transitionName = transitionName

        val transition = TransitionInflater.from(this).inflateTransition(android.R.transition.move)
        window.sharedElementReturnTransition = transition


        Glide.with(binding.ivPokemonImage)
            .load(pokemon.imageurl)
            .into(binding.ivPokemonImage)
    }

    private fun initializeViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabsArray[position]
        }.attach()
    }

    private fun setBackButton() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}
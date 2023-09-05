package com.affinity.pokedex.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModelProvider
import com.affinity.pokedex.adapter.ViewPagerAdapter
import com.affinity.pokedex.databinding.ActivityPokemonBinding
import com.affinity.pokedex.model.Pokemon
import com.affinity.pokedex.utility.ColorUtils
import com.affinity.pokedex.utility.TypeUtils
import com.affinity.pokedex.viewmodel.PokemonViewModel
import com.affinity.pokedex.viewmodel.PokemonViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class PokemonActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPokemonBinding

    private lateinit var pokemonList: List<Pokemon>

    private val tabsArray = arrayOf("About", "Stats", "Evolution")

    val pokemonMvvm : PokemonViewModel by lazy{
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



//        initializeShadowImage() // Takes too much RAM


    }


    private fun initializeTopImage() {

        val pokemonType1 = pokemonList[0].typeofpokemon[0]
        val color = ColorUtils.getColorForString(pokemonType1)
        Log.d("COLOR", color)
        val pokemonType1Icon = TypeUtils.typeMap[pokemonType1]

        binding.apply {
            tvPokemonName.text = pokemonList[0].name
            tvPokemonName2.text = pokemonList[0].name
            tvPokemonId.text = pokemonList[0].id

            mainBackground.setBackgroundColor(Color.parseColor("#DA$color"))

            // Setting 1st Type Card
            tvPokemonType1.text = pokemonType1
            cvPokemonType1.background.setTint(Color.parseColor("#$color"))
        }

        Glide.with(binding.root)
            .load(pokemonType1Icon)
            .into(binding.ivPokemonType1)

        // Setting 2nd Type Card
        if(pokemonList[0].typeofpokemon.size > 1){
            binding.cvPokemonType2.visibility = View.VISIBLE

            val pokemonType2 = pokemonList[0].typeofpokemon[1]
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

    // Getting intent data and setting animation
    private fun setImageTransitionAnim() {
        val extras = intent.extras
        val transitionName = extras?.getString("TRANSITION_NAME")
        Log.d("TRANSITION", transitionName.toString())
        val data = extras?.getString("POKEMON")
        val source = extras?.getString("SOURCE")
        pokemonList = Json.decodeFromString<List<Pokemon>>(data!!)
        pokemonMvvm.setCurrentPokemonList(pokemonList)
        ViewCompat.setTransitionName(binding.ivPokemonImage, transitionName)

        // Start the shared element transition
//        supportPostponeEnterTransition()
        binding.ivPokemonImage.doOnPreDraw {
            supportStartPostponedEnterTransition()
        }
//        binding.targetImageView.transitionName = transitionName

        when (source) {
            "RV" -> {
                // For Return transition
                val transition = TransitionInflater.from(this).inflateTransition(android.R.transition.move)
                window.sharedElementReturnTransition = transition
            }
            "NOT_RV" -> {
                val msg = "${pokemonList[0].name} Collected"
                Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
                // For Return transition
                val transition = TransitionInflater.from(this).inflateTransition(android.R.transition.move)
                window.sharedElementReturnTransition = transition
            }
            else -> {
                window.sharedElementReturnTransition = null
                window.sharedElementReenterTransition = null
                binding.ivPokemonImage.transitionName = null
            }
        }



        Glide.with(binding.ivPokemonImage)
            .load(pokemonList[0].imageurl)
            .apply(RequestOptions().downsample(DownsampleStrategy.AT_MOST))
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
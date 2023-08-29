package com.example.pokedex.fragments

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.example.pokedex.activity.PokemonActivity
import com.example.pokedex.databinding.FragmentAboutBinding
import com.example.pokedex.model.Pokemon
import com.example.pokedex.utility.ColorUtils
import com.example.pokedex.viewmodel.PokemonViewModel


class AboutFragment : Fragment() {

    private lateinit var binding : FragmentAboutBinding

    private lateinit var pokemonMvvm: PokemonViewModel

    private lateinit var bitmap : Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pokemonMvvm = (activity as PokemonActivity).pokemonMvvm

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemon = pokemonMvvm.getCurrentPokemonList()[0]
        val mainColor = ColorUtils.getColorForString(pokemon.typeofpokemon[0])


        loadSprites(pokemon)

        setAllData(pokemon, mainColor)




    }

    private fun setAllData(pokemon: Pokemon, mainColor: String) {
        binding.apply {

            tvAbout.text = pokemon.xdescription
            tvHead1.setTextColor(Color.parseColor("#$mainColor"))
            tvPokemonName3.text = pokemon.name
            tvPokemonName3.setTextColor(Color.parseColor("#$mainColor"))

            tvMalePercent.text = pokemon.percent_male
            tvFemalePercent.text = pokemon.percent_female
            tvEggGroups.text = pokemon.egg_groups
            tvClassification.text = pokemon.classification

            val eggCycle = pokemon.cycles + " (${pokemon.base_egg_steps} Steps)"
            tvEggCycle.text = eggCycle

            tvHead2.setTextColor(Color.parseColor("#$mainColor"))
            tvBaseExp.text = pokemon.base_exp
            tvAbilities.text = getAbilities(pokemon)
            val baseFriendship = pokemon.base_friendship + " (${pokemon.status})"
            tvBaseFriendship.text = baseFriendship
            tvHeight.text = pokemon.height
            tvWeight.text = pokemon.weight
            tvGrowthRate.text = pokemon.growth_rate
        }
    }

    private fun getAbilities(pokemon: Pokemon) : String{
        var abilities = ""
        for(ability in pokemon.abilities){
            abilities += "$ability, "
        }

        return abilities.substring(0, abilities.length - 2) + "\nHidden: " + pokemon.abilities_hidden
    }

    private fun loadSprites(pokemon : Pokemon) {
        val baseUrl ="https://img.pokemondb.net/sprites/black-white/anim/"
//                "normal/charmander.gif"
        val frontUrl = baseUrl + "normal/" + pokemon.name.lowercase() + ".gif"
        val backUrl = baseUrl + "back-normal/" + pokemon.name.lowercase() + ".gif"

        val shinyFrontUrl = baseUrl + "shiny/" + pokemon.name.lowercase() + ".gif"
        val shinyBackUrl = baseUrl + "back-shiny/" + pokemon.name.lowercase() + ".gif"


        Glide.with(requireActivity())
            .asGif()
            .load(frontUrl)
            .transition(withCrossFade())
//            .placeholder(placeholderDrawable)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .apply(RequestOptions().downsample(DownsampleStrategy.AT_MOST))
            .into(binding.ivFrontSprite)

        Glide.with(requireActivity())
            .asGif()
            .load(backUrl)
            .transition(withCrossFade())
//            .placeholder(placeholderDrawable)
            .apply(RequestOptions().downsample(DownsampleStrategy.AT_MOST))
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(binding.ivBackSprite)

        Glide.with(requireActivity())
            .asGif()
            .load(shinyFrontUrl)
            .transition(withCrossFade())
//            .placeholder(placeholderDrawable)
            .apply(RequestOptions().downsample(DownsampleStrategy.AT_MOST))
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(binding.ivShinyFrontSprite)

        Glide.with(requireActivity())
            .asGif()
            .load(shinyBackUrl)
            .transition(withCrossFade())
//            .placeholder(placeholderDrawable)
            .apply(RequestOptions().downsample(DownsampleStrategy.AT_MOST))
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(binding.ivShinyBackSprite)


        // Set the Bitmap to your ImageView
//        binding.ivFrontSprite.setImageBitmap(bitmap)
//        binding.ivBackSprite.setImageBitmap(bitmap)
//        binding.ivShinyFrontSprite.setImageBitmap(bitmap)
//        binding.ivShinyBackSprite.setImageBitmap(bitmap)


    }

    fun loadImageUsingGlide(url : String, view : ImageView, placeHolder : Drawable){
        Glide.with(requireActivity())
            .asGif()
            .load(url)
            .transition(withCrossFade())
            .placeholder(placeHolder)
            .error(placeHolder)
            .apply(RequestOptions().downsample(DownsampleStrategy.AT_MOST))
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(view)

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
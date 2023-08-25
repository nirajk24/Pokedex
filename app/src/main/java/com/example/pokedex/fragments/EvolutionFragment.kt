package com.example.pokedex.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.pokedex.activity.PokemonActivity
import com.example.pokedex.databinding.FragmentEvolutionBinding
import com.example.pokedex.model.Pokemon
import com.example.pokedex.utility.ColorUtils
import com.example.pokedex.viewmodel.PokemonViewModel

class EvolutionFragment : Fragment() {

    private lateinit var binding: FragmentEvolutionBinding
    private lateinit var pokemonMvvm: PokemonViewModel

    private lateinit var pokemonEvolutionList : List<Pokemon>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEvolutionBinding.inflate(layoutInflater, container, false)

        pokemonMvvm = (activity as PokemonActivity).pokemonMvvm

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemonEvolutionList = pokemonMvvm.getCurrentPokemonList()

        val mainColor = ColorUtils.getColorForString(pokemonEvolutionList[0].typeofpokemon[0])
        binding.tvHead1.setTextColor(Color.parseColor("#$mainColor"))

        setFirstEvolution()
        setSecondEvolution(mainColor)
    }


    private fun setFirstEvolution() {
        if(pokemonEvolutionList.size > 2){ // At least 3 elements exist - 1st element is curr Pokemon
            binding.layoutNoEvolution.visibility = View.INVISIBLE
            binding.layoutEvolution1.visibility = View.VISIBLE
            binding.apply {
                tvPokemonName1.text = pokemonEvolutionList[1].name
                tvPokemonName2.text = pokemonEvolutionList[2].name
                val reason = pokemonEvolutionList[2].reason.trim()
                tvReason1.text = reason.substring(1, reason.length - 1)

                Glide.with(requireActivity())
                    .load(pokemonEvolutionList[1].imageurl)
                    .apply(RequestOptions().downsample(DownsampleStrategy.AT_MOST))
                    .into(ivPokemon1)

                Glide.with(requireActivity())
                    .load(pokemonEvolutionList[2].imageurl)
                    .apply(RequestOptions().downsample(DownsampleStrategy.AT_MOST))
                    .into(ivPokemon2)
            }
        }
    }

    private fun setSecondEvolution(mainColor: String) {
        if(pokemonEvolutionList.size > 3){ // At least 4 elements exist - 1st element is curr Pokemon
            binding.apply {
                layoutEvolution2.visibility = View.VISIBLE
                tvPokemonName.visibility = View.VISIBLE
                tvPokemonName.text = pokemonEvolutionList[0].name


                tvPokemonName.setTextColor(Color.parseColor("#$mainColor"))
            }

            binding.apply {
                tvPokemonName3.text = pokemonEvolutionList[2].name
                tvPokemonName4.text = pokemonEvolutionList[3].name
                val reason2 = pokemonEvolutionList[3].reason.trim()
                tvReason2.text = reason2.substring(1, reason2.length - 1)

                Glide.with(requireActivity())
                    .load(pokemonEvolutionList[2].imageurl)
                    .apply(RequestOptions().downsample(DownsampleStrategy.AT_MOST))
                    .into(ivPokemon3)

                Glide.with(requireActivity())
                    .load(pokemonEvolutionList[3].imageurl)
                    .apply(RequestOptions().downsample(DownsampleStrategy.AT_MOST))
                    .into(ivPokemon4)
            }
        }
    }


}
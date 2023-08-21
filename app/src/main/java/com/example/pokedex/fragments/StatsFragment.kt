package com.example.pokedex.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ClipDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.PokemonActivity
import com.example.pokedex.R
import com.example.pokedex.adapter.EffectivenessAdapter
import com.example.pokedex.databinding.FragmentStatsBinding
import com.example.pokedex.model.Pokemon
import com.example.pokedex.utility.ColorUtils
import com.example.pokedex.viewmodel.PokemonViewModel
import com.google.android.material.progressindicator.LinearProgressIndicator

class StatsFragment : Fragment() {

    private lateinit var binding : FragmentStatsBinding

    private lateinit var pokemonMvvm : PokemonViewModel

    private lateinit var pokemon : Pokemon
    private lateinit var mainColor : String

    private lateinit var effectivenessAdapter: EffectivenessAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatsBinding.inflate(inflater, container, false)

        pokemonMvvm = (activity as PokemonActivity).pokemonMvvm

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pokemon = pokemonMvvm.getCurrentPokemonList()[0]
        mainColor = ColorUtils.getColorForString(pokemon.typeofpokemon[0])

        setBaseStats()

        initializeRecyclerView()



        setTextsTitle()



    }

    private fun setTextsTitle() {
        val textInfo = "The effectiveness of each type on ${pokemon.name}"
        binding.tvInfo.text = textInfo
        binding.tvHead2.setTextColor(Color.parseColor("#$mainColor"))

        binding.tvPokemonName2.text = pokemon.name
        binding.tvPokemonName2.setTextColor(Color.parseColor("#$mainColor"))
    }

    private fun initializeRecyclerView() {
        effectivenessAdapter = EffectivenessAdapter()
        binding.rvEffectiveness.apply {
            layoutManager = GridLayoutManager(context, 9, GridLayoutManager.VERTICAL, false)
            adapter = effectivenessAdapter
        }
        effectivenessAdapter.differ.submitList(pokemon.effectiveness)
        Log.d("RV", "DATA SENT")
    }

    private fun setBaseStats() {
        binding.apply {
            tvHead1.setTextColor(Color.parseColor("#$mainColor"))

            tvHp.text = pokemon.hp.toString()
            setProgressBar(pbHp, pokemon.hp, 150)

            tvAttack.text = pokemon.attack.toString()
            setProgressBar(pbAttack, pokemon.attack, 150)

            tvDefense.text = pokemon.defense.toString()
            setProgressBar(pbDefense, pokemon.defense, 150)

            tvSpAttack.text = pokemon.sp_attack.toString()
            setProgressBar(pbSpAttack, pokemon.sp_attack, 150)

            tvSpDefense.text = pokemon.sp_defense.toString()
            setProgressBar(pbSpDefense, pokemon.sp_defense, 150)

            tvSpeed.text = pokemon.speed.toString()
            setProgressBar(pbSpeed, pokemon.speed, 150)

            tvTotal.text = pokemon.total_points.toString()
            setProgressBar(pbTotal, pokemon.total_points, 900)
        }
    }

    private fun setProgressBar(pb : LinearProgressIndicator, current : Int, max : Int) {

        pb.max = max
        pb.progress = current
        pb.progressTintList = ColorStateList.valueOf(resources.getColor(R.color.Bug))

        pb.setIndicatorColor(Color.parseColor("#$mainColor"))
    }




}
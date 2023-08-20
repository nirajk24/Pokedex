package com.example.pokedex.adapter

import android.content.Intent
import android.graphics.Color
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.MainActivity
import com.example.pokedex.PokemonActivity
import com.example.pokedex.databinding.PokemonCard2Binding
import com.example.pokedex.databinding.PokemonCardBinding
import com.example.pokedex.model.Pokemon
import com.example.pokedex.utility.ColorUtils
import com.example.pokedex.viewmodel.MainViewModel
import kotlinx.serialization.Serializable

class PokemonAdapter() : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {


    lateinit var onItemClick : ((Pokemon, String, View) -> Unit)


    class PokemonViewHolder(val binding : PokemonCard2Binding)
        : RecyclerView.ViewHolder(binding.root)


    private val diffUtil = object: DiffUtil.ItemCallback<Pokemon>(){  // Created an object of DiffUtil ItemCallBack class
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            // checks if the items are same
            return oldItem.id == newItem.id  // returns true if item id is same
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            // check if the contents of item same
            return oldItem == newItem  // returns true if the items are same
        }
    }
    // does something
    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            PokemonCard2Binding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {


        val pokemon = differ.currentList[position]

        ViewCompat.setTransitionName(holder.binding.ivPokemonThumb, pokemon.name);

        Glide.with(holder.itemView)
            .load(pokemon.imageurl)
            .into(holder.binding.ivPokemonThumb)

        holder.binding.apply {
            tvPokemonName.text = pokemon.name
            tvPokemonId.text = pokemon.id
            val pokemonType = pokemon.typeofpokemon[0]

            val color = ColorUtils.getColorForString(pokemonType)

            cvMainCard.background.setTint(Color.parseColor(color))

            tvPokemonType1.text = pokemon.typeofpokemon[0]
            if(pokemon.typeofpokemon.size > 1){
                cvPokemonType2.visibility = View.VISIBLE
                tvPokemonType2.text = pokemon.typeofpokemon[1]
            }
        }

        holder.itemView.setOnClickListener {
            onItemClick.invoke(pokemon, "", holder.binding.ivPokemonThumb)
        }
    }
}
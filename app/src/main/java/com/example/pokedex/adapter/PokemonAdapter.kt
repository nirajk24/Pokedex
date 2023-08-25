package com.example.pokedex.adapter

import android.content.res.AssetManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.pokedex.databinding.PokemonCard2Binding
import com.example.pokedex.databinding.PokemonCardBinding
import com.example.pokedex.model.PokemonSmall
import com.example.pokedex.utility.ColorUtils
import com.example.pokedex.utility.TypeUtils
import java.io.IOException

class PokemonAdapter(private val assetManager: AssetManager) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {


    lateinit var onItemClick : ((PokemonSmall, String, View) -> Unit)


    class PokemonViewHolder(val binding : PokemonCard2Binding)
        : RecyclerView.ViewHolder(binding.root)


    private val diffUtil = object: DiffUtil.ItemCallback<PokemonSmall>(){  // Created an object of DiffUtil ItemCallBack class
        override fun areItemsTheSame(oldItem: PokemonSmall, newItem: PokemonSmall): Boolean {
            // checks if the items are same
            return oldItem.id == newItem.id  // returns true if item id is same
        }

        override fun areContentsTheSame(oldItem: PokemonSmall, newItem: PokemonSmall): Boolean {
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
            .apply(RequestOptions().downsample(DownsampleStrategy.AT_MOST))
            .into(holder.binding.ivPokemonThumb)

        holder.binding.apply {
            tvPokemonName.text = pokemon.name
            tvPokemonId.text = pokemon.id
            val pokemonType1 = pokemon.typeofpokemon[0]

            val color = ColorUtils.getColorForString(pokemonType1)
            cvMainCard.background.setTint(Color.parseColor("#FF$color"))

            // Setting 1st Type Card
            tvPokemonType1.text = pokemonType1
            val pokemonType1Icon = TypeUtils.typeMap[pokemonType1]
            cvPokemonType1.background.setTint(Color.parseColor("#FF$color"))
            Glide.with(holder.itemView)
                .load(pokemonType1Icon)
                .into(ivPokemonType1)

            // Setting 2nd Type Card
            if(pokemon.typeofpokemon.size > 1){
                cvPokemonType2.visibility = View.VISIBLE

                val pokemonType2 = pokemon.typeofpokemon[1]
                tvPokemonType2.text = pokemonType2

                val color2 = ColorUtils.getColorForString(pokemonType2)
                cvPokemonType2.background.setTint(Color.parseColor("#99$color2"))

                val pokemonType2Icon = TypeUtils.typeMap[pokemonType2]
                Glide.with(holder.itemView)
                    .load(pokemonType2Icon)
                    .into(ivPokemonType2)
            }
        }

        holder.itemView.setOnClickListener {
            onItemClick.invoke(pokemon, "", holder.binding.ivPokemonThumb)
        }

    }
}
package com.example.pokedex.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.databinding.ItemTypeBinding
import com.example.pokedex.utility.ColorUtils

class EffectivenessAdapter() : RecyclerView.Adapter<EffectivenessAdapter.EffectivenessViewHolder>() {

    class EffectivenessViewHolder(val binding : ItemTypeBinding)
        : RecyclerView.ViewHolder(binding.root)

    private val typeList = ColorUtils.getAllKeys()

    private val numberMapping = mapOf(
        0 to "0",
        25 to "1/4",
        50 to "1/2",
        100 to "1",
        200 to "2",
        400 to "4"
    )

    private val diffUtil = object: DiffUtil.ItemCallback<Int>(){  // Created an object of DiffUtil ItemCallBack class
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            // checks if the items are same
            return oldItem + 10 == newItem  // returns true if item id is same
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            // check if the contents of item same
            return oldItem + 10 == newItem  // returns true if the items are same
        }
    }
    // does something
    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EffectivenessViewHolder {
        return EffectivenessViewHolder(
            ItemTypeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
            )
    }

    override fun getItemCount(): Int {
        return 18
    }

    override fun onBindViewHolder(holder: EffectivenessViewHolder, position: Int) {
        val typeColor = ColorUtils.getColorForString(typeList[position])
        val typeText = typeList[position].substring(0, 3).uppercase()
        holder.binding.apply {
            cvType.background.setTint(Color.parseColor("#$typeColor"))
            tvType.text = typeText

            val effect = differ.currentList[position]
            if(effect != 100){
                tvEffectiveness.text = numberMapping[effect]

                if(effect == 200)
                    ivCircle.setImageResource(R.color.good)
                else if(effect == 0)
                    ivCircle.setImageResource(R.color.neutral)
                else if(effect == 50)
                    ivCircle.setImageResource(R.color.bad)
                else if(effect == 25)
                    ivCircle.setImageResource(R.color.very_bad)
                else if(effect == 400)
                    ivCircle.setImageResource(R.color.very_good)
            }
        }
    }
}
package com.example.pokedex.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.activity.AvatarActivity
import com.example.pokedex.databinding.ItemAvatarBinding
import com.example.pokedex.repository.MyPreferences

class AvatarAdapter(private val pf : MyPreferences) : RecyclerView.Adapter<AvatarAdapter.AvatarViewHolder>() {


    class AvatarViewHolder(val binding : ItemAvatarBinding) : RecyclerView.ViewHolder(binding.root)

    private var selectedItemPosition = pf.currentAvatar


    lateinit var onItemClick : ((Int) -> Unit)

    fun setSelectedItemPosition(position: Int) {
        selectedItemPosition = position
        notifyDataSetChanged() // Notify adapter to update the view
    }

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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarViewHolder {
        return AvatarViewHolder(ItemAvatarBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: AvatarViewHolder, position: Int) {

        if (position == selectedItemPosition) {
            // Apply the highlight effect
            holder.binding.cvAvatarItem.setCardBackgroundColor(ContextCompat
                .getColor(holder.itemView.context, R.color.accent))
        } else {
            // Reset the background for non-selected items
            holder.binding.cvAvatarItem.setCardBackgroundColor(ContextCompat
                .getColor(holder.itemView.context, android.R.color.transparent))
        }


        holder.binding.apply {
            Glide.with(holder.itemView)
                .load(differ.currentList[position])
                .into(ivAvatarItem)

        }

        holder.itemView.setOnClickListener {
            onItemClick(position)
            setSelectedItemPosition(position)
        }


    }
}
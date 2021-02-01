package com.gowtham.mvipokedex.adpters

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gowtham.mvipokedex.adpters.AdPokemonType.MyViewHolder
import com.gowtham.mvipokedex.databinding.RowPokemonTypeBinding
import com.gowtham.mvipokedex.util.PokemonTypeUtils


class AdPokemonType : ListAdapter<String, MyViewHolder>(PokemonTypeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RowPokemonTypeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {
        val name = getItem(position)
        if (name != null)
            myViewHolder.bind(name)
    }

    class MyViewHolder(val binding: RowPokemonTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(name: String) {
            binding.name = name
            binding.txtName.getBackground().setColorFilter(
                binding.txtName.context.resources.getColor(PokemonTypeUtils.getTypeColor(name)),
                PorterDuff.Mode.SRC_ATOP
            )
            binding.executePendingBindings()
        }
    }
}

class PokemonTypeDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}
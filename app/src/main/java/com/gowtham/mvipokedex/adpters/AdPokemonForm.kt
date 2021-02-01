package com.gowtham.mvipokedex.adpters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gowtham.mvipokedex.databinding.RowPokemonFormBinding


class AdPokemonForm : ListAdapter<String, AdPokemonForm.MyViewHolder>(PokemonTypeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RowPokemonFormBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {
        val item = getItem(position)
        if(item!=null)
           myViewHolder.bind(item)
    }

    class MyViewHolder(val binding: RowPokemonFormBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.name = item
            binding.executePendingBindings()
        }
    }
}

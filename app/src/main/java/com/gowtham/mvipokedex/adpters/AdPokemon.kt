package com.gowtham.mvipokedex.adpters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.gowtham.mvipokedex.databinding.RowPokemonBinding
import com.gowtham.mvipokedex.models.Pokemon

typealias onCardListener= (cardView: MaterialCardView, pokemon: Pokemon)-> Unit

class AdPokemon : PagingDataAdapter<Pokemon, AdPokemon.MyViewHolder>(PokemonDiffCallback()) {

    private lateinit var callback: onCardListener

    companion object{
        val LOADING_ITEM = 0

        val MOVIE_ITEM=1
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RowPokemonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {
        val item = getItem(position)
        if(item!=null)
           myViewHolder.bind(item,callback)
    }

    fun setCallBack(function: onCardListener) {
        this.callback=function
    }

    class MyViewHolder(val binding: RowPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Pokemon, callback: onCardListener) {
            binding.pokemon = item
            itemView.setOnClickListener {
                ViewCompat.setTransitionName(binding.cardView,"myTransName")
                callback(binding.cardView,item)
            }
            binding.executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position==itemCount) MOVIE_ITEM else LOADING_ITEM
    }
}

class PokemonDiffCallback : DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem == newItem
    }
}
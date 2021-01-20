package com.gowtham.mvisample.adpters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gowtham.mvisample.databinding.RowListBinding
import com.gowtham.mvisample.models.Passenger

class AdPassenger : ListAdapter<Passenger, AdPassenger.MyViewHolder>(PojoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(RowListBinding.bind(parent.rootView))
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {
        val item = getItem(position)
        myViewHolder.bind(item)
    }

    class MyViewHolder(val binding: RowListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Passenger) {
            binding.passenger = item
            binding.executePendingBindings()
        }
    }
}

class PojoDiffCallback : DiffUtil.ItemCallback<Passenger>() {
    override fun areItemsTheSame(oldItem: Passenger, newItem: Passenger): Boolean {
        return oldItem._id == newItem._id
    }

    override fun areContentsTheSame(oldItem: Passenger, newItem: Passenger): Boolean {
        return oldItem == newItem
    }
}
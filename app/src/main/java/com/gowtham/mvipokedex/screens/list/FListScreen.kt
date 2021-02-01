package com.gowtham.mvipokedex.screens.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.card.MaterialCardView
import com.gowtham.mvipokedex.R
import com.gowtham.mvipokedex.adpters.AdPokemon
import com.gowtham.mvipokedex.adpters.PokemonLoadStateAdapter
import com.gowtham.mvipokedex.databinding.FListScreenBinding
import com.gowtham.mvipokedex.models.Passenger
import com.gowtham.mvipokedex.models.Pokemon
import com.gowtham.mvipokedex.util.MainState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FListScreen : Fragment(R.layout.f_list_screen) {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: FListScreenBinding

    private lateinit var listOfPassenger: ArrayList<Passenger>

    private val adPokemon: AdPokemon by lazy {
        AdPokemon()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FListScreenBinding.bind(view)

        setDataInView()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when(it){
                    MainState.Idle -> {}
                    MainState.Loading -> {
                    }
                    is MainState.Success -> {
                        adPokemon.submitData(viewLifecycleOwner.lifecycle, it.data as PagingData<Pokemon>)
                    }
                    is MainState.Failure -> {
                    }
                }
            }
        }
    }

    private fun setDataInView() {
        listOfPassenger=ArrayList()
        val gridLayoutManager=GridLayoutManager(context, 2)
        binding.listView.apply {
            layoutManager = gridLayoutManager
            adapter = adPokemon.withLoadStateHeaderAndFooter(
                header = PokemonLoadStateAdapter { adPokemon.retry() },
                footer = PokemonLoadStateAdapter { adPokemon.retry() },
            )
        }

        gridLayoutManager.spanSizeLookup=object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adPokemon.getItemViewType(position) == AdPokemon.LOADING_ITEM) 1 else 2
            }
        }

        adPokemon.setCallBack { v: MaterialCardView, poke: Pokemon ->
            val extras = FragmentNavigatorExtras(
               v to "myTransName"
            )
            val action= FListScreenDirections.actionFListScreenToFDetail(poke)
            findNavController().navigate(action)
        }

        binding.buttonRetry.setOnClickListener {
            adPokemon.retry()
        }

        adPokemon.addLoadStateListener { loadState ->
            binding.apply {
                progressView.isVisible = loadState.source.refresh is LoadState.Loading
                listView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adPokemon.itemCount < 1) {
                    listView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}
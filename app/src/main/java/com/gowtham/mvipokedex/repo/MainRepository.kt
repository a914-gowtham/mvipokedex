package com.gowtham.mvipokedex.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.gowtham.mvipokedex.api.ApiHelperImpl
import com.gowtham.mvipokedex.models.Pokemon
import com.gowtham.mvipokedex.util.PokemonPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelperImpl: ApiHelperImpl) {

   suspend fun getPokemonDetail(name: String) = apiHelperImpl.fetchPokemonInfo(name)

    fun getPokemonList(): LiveData<PagingData<Pokemon>> {
        Log.d("TAG", "getPokemonList: list api called")
       return Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PokemonPagingSource(apiHelperImpl) }
        ).liveData
    }

    fun getPokemonListAsFlow(): Flow<PagingData<Pokemon>> {
        Log.d("TAG", "getPokemonList: list api called")
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PokemonPagingSource(apiHelperImpl) }
        ).flow
    }

}
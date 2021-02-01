package com.gowtham.mvipokedex.util

import android.util.Log
import androidx.paging.PagingSource
import com.gowtham.mvipokedex.api.ApiHelperImpl
import com.gowtham.mvipokedex.models.Pokemon
import retrofit2.HttpException
import java.io.IOException

private const val START_OFFSET = 0

class PokemonPagingSource(private val apiHelperImpl: ApiHelperImpl) : PagingSource<Int, Pokemon>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {

        val position = params.key ?: START_OFFSET

        Log.d("TAG", "last called position: $position")

        return try {
            val response = apiHelperImpl.getPokemonList(position*10)
            val pokemonList = response.body()!!.results as ArrayList
            LoadResult.Page(
                data = pokemonList,
                prevKey = if (position == START_OFFSET) null else position - 1,
                nextKey = if (pokemonList.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
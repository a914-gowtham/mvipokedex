package com.gowtham.mvipokedex.util

import com.squareup.moshi.Moshi

object Utils {

    fun getMoshiBuilder() = Moshi.Builder().build()

    //convert a data class to a string
    inline fun <reified T> T.toJson(): String {
        val jsonAdapter = getMoshiBuilder().adapter(T::class.java)
        return jsonAdapter.toJson(this)
    }

    //convert a string to data class
    inline fun <reified O> String.fromJson(): O {
        val jsonAdapter = getMoshiBuilder().adapter(O::class.java)
        return jsonAdapter.fromJson(this)!!
    }

}
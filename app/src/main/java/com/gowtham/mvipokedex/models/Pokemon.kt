package com.gowtham.mvipokedex.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Pokemon(
    var page: Int = 0,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "url") val url: String
): Parcelable {

    fun getImageUrl(): String {
        return "https://pokeres.bastionbot.org/images/pokemon/${getIndex()}.png"
    }

    fun getIndex(): String{
        return url.split("/".toRegex()).dropLast(1).last()
    }
}

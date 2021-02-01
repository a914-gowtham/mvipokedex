package com.gowtham.mvipokedex.models

import com.squareup.moshi.Json

data class PokemonDetail(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "weight") val weight: Int,
    @field:Json(name = "height") val height: Int,
    @field:Json(name = "types") val types: List<Type>,
    @field:Json(name = "forms") val forms: List<Pokemon>,
    @field:Json(name = "moves") val moves: List<Move>,
    @field:Json(name = "abilities") val abilities: List<Ability>,
    )

data class Type(
    @field:Json(name = "slot") val slot: Int,
    @field:Json(name = "type") val type: Pokemon
)

data class Move(
    @field:Json(name = "move") val move: Pokemon
)

data class Ability(
    @field:Json(name = "slot") val slot: Int,
    @field:Json(name = "ability") val type: Pokemon
)

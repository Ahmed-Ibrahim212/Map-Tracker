package com.olamachia.maptrackerweekeighttask

import com.squareup.moshi.Json

data class Pokemon(
    @field:Json(name = "height")
    val height: String,
    @field:Json(name = "weight")
    val weight: String,
    @field:Json(name = "base_experience")
    val base_experience: String,
    @field:Json(name = "order")
    val order: String,
    @field:Json(name = "species")
    val species: Species,
    @field:Json(name = "sprites")
    val sprites: Sprites,
    @field:Json(name = "stats")
    val stats: List<Stats>,
    @field:Json(name = "moves")
    val moves: List<Moves>
)
data class Moves(
    @field:Json(name = "move")
    val move: Move
)
data class Move(
    @field:Json(name = "name")
    val name: String
)
data class Stats(
    @field:Json(name = "base_stat")
    val base_stat: String
)
data class Sprites(
    @field:Json(name = "back_default")
    val back_default: String,
    @field:Json(name = "back_shiny")
    val back_shiny: String,
    @field:Json(name = "front_shiny")
    val front_shiny: String
)
data class Species(
    @field:Json(name = "name")
    val name: String
)


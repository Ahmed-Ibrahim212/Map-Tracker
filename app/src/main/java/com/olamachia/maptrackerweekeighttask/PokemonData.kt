package com.olamachia.maptrackerweekeighttask

import com.squareup.moshi.Json

data class PokemonData (
    @field:Json(name = "results")
    val results: List<ResultsData>
        )

data class  ResultsData(
    @field: Json(name = "name")
    val name: String,

    @field:Json(name = "url")
    val url: String
)
package com.olamachia.maptrackerweekeighttask

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonInterface {

    @GET ( "pokemon")
    suspend fun getPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int

    ): PokemonData

    // get pokemon details
    @GET("pokemon/{pokemon}")
    suspend fun getPokemonDetails(@Path("pokemon") pokemon: String): Pokemon
}
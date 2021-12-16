package com.olamachia.maptrackerweekeighttask


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl("https://pokeapi.co/api/v2/")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

object RetrofitClient {
    val retrofitService: PokemonInterface by lazy {
        retrofit.create(PokemonInterface::class.java)
    }
}



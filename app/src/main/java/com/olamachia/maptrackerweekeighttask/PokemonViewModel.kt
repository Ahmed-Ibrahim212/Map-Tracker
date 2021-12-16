package com.olamachia.maptrackerweekeighttask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PokemonViewModel: ViewModel() {

//    LiveData for getting pokemon data
    private val _pokemonData = MutableLiveData<PokemonData>()
    val pokemonData: LiveData<PokemonData>
    get() = _pokemonData

    fun getPokemon(limit: Int, offset: Int){

        viewModelScope.launch {
            try {
                val listResult = RetrofitClient.retrofitService.getPokemon(limit, offset)

                _pokemonData.value = listResult
            }catch (e: Exception){
                println("An error occurred")
            }
        }
    }
}
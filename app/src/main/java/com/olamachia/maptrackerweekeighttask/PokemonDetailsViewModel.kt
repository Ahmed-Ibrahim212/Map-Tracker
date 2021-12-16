package com.olamachia.maptrackerweekeighttask

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class PokemonDetailsViewModel() : ViewModel() {
    private var _pokemonIndividualData = MutableLiveData<Pokemon>()

    var pokemonIndividualData: LiveData<Pokemon> = _pokemonIndividualData

    fun getPokemonIndividualDataDetails(): LiveData<Pokemon> {
        return _pokemonIndividualData
    }

    /**
     * Use kotlin coroutine to make network call and get json results of each pokemon
     */
    fun getPokemonIndividualDetails(pokemonId: String) {

        viewModelScope.launch {
            try {
                val pokemonIndividualResults =
                    RetrofitClient.retrofitService.getPokemonDetails(pokemonId)
                _pokemonIndividualData.value = pokemonIndividualResults
            } catch (e: Exception) {
                Log.d("FAILUREDETAILS", "$e")
            }
        }
    }
}
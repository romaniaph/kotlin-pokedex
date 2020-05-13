package com.example.kotlinpokedex.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinpokedex.data.PokemonResult
import com.example.kotlinpokedex.data.model.Pokemon
import com.example.kotlinpokedex.data.repository.PokemonRepository

class PokemonViewModel(private val dataSource: PokemonRepository) : ViewModel() {
    val liveDataPokemonList: MutableLiveData<List<Pokemon>> = MutableLiveData()
    var liveDataLoading: MutableLiveData<Boolean> = MutableLiveData()
    var liveDataPokemon: MutableLiveData<Pokemon> = MutableLiveData()
    private var offset: Int = 1

    class ViewModelFactory(private val dataSource: PokemonRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PokemonViewModel::class.java))
                return PokemonViewModel(dataSource) as T
            else
                throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }

    fun getPokemons() {
        if (liveDataLoading.value == null) liveDataLoading.value = false

        if (liveDataLoading.value == false) {
            liveDataLoading.value = true

            dataSource.getPokemons(offset) {
                when (it) {
                    is PokemonResult.GetPokemonsSuccess -> loadPokemonList(it.pokemons)
                    is PokemonResult.Failed -> retryGetPokemons()
                }
            }
        }
    }

    private fun retryGetPokemons() {
        liveDataPokemonList.value = listOf()
        liveDataLoading.value = false
        getPokemons()
    }

    private fun loadPokemonList(pokemons: List<Pokemon>?) {
        liveDataPokemonList.value = pokemons
        offset += 21
        liveDataLoading.value = false
    }

    fun getPokemon(id: String) {
        liveDataLoading.value = true

        dataSource.getPokemon(id) {
            when (it) {
                is PokemonResult.GetPokemonSuccess -> loadPokemon(it.pokemon)
                is PokemonResult.Failed -> loadPokemon(null)
            }
        }
    }

    private fun loadPokemon(pokemon: Pokemon?) {
        liveDataPokemon.value = pokemon
        liveDataLoading.value = false
    }

}

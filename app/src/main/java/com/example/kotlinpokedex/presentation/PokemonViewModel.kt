package com.example.kotlinpokedex.presentation


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinpokedex.data.ReactPokedexFactory
import com.example.kotlinpokedex.data.model.Pokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonViewModel : ViewModel() {
    val liveDataPokemonList: MutableLiveData<List<Pokemon>> = MutableLiveData()
    var liveDataLoading: MutableLiveData<Boolean> = MutableLiveData()
    var liveDataPokemon: MutableLiveData<Pokemon> = MutableLiveData()
    private var offset: Int = 1

    fun getPokemons() {

        if (liveDataLoading.value == null) liveDataLoading.value = false

        if (liveDataLoading.value == false) {
            liveDataLoading.value = true

            ReactPokedexFactory.service.getPokemons(offset.toString())
                .enqueue(object : Callback<List<Pokemon>> {
                    override fun onResponse(
                        call: Call<List<Pokemon>>,
                        response: Response<List<Pokemon>>
                    ) {
                        response.body()?.let { loadPokemonList(it) }
                    }

                    override fun onFailure(call: Call<List<Pokemon>>, t: Throwable) {
                        liveDataPokemonList.value = listOf()
                        liveDataLoading.value = false
                    }
                })
        }
    }

    private fun loadPokemonList(pokemons: List<Pokemon>) {
        liveDataPokemonList.value = pokemons
        offset += 21
        liveDataLoading.value = false
    }

    fun getPokemon(id: String) {
        liveDataLoading.value = true

        ReactPokedexFactory.service.getPokemon(id.toString()).enqueue(object : Callback<Pokemon> {
            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                loadPokemon(null)
            }

            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                response.body()?.let {
                    loadPokemon(it)
                } ?: loadPokemon(null)
            }
        })
    }

    private fun loadPokemon(pokemon: Pokemon?) {
        liveDataPokemon.value = pokemon
        liveDataLoading.value = false
    }

}

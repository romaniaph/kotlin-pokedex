package com.example.kotlinpokedex.data.repository

import android.util.Log
import com.example.kotlinpokedex.data.PokemonResult
import com.example.kotlinpokedex.data.ReactPokedexFactory
import com.example.kotlinpokedex.data.model.Pokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonApiDataSource : PokemonRepository {
    override fun getPokemons(offset: Int, pokemonResultCallback: (pokemon: List<Pokemon>?) -> Unit) {
        ReactPokedexFactory.service.getPokemons(offset.toString())
            .enqueue(object : Callback<List<Pokemon>> {
                override fun onResponse(
                    call: Call<List<Pokemon>>,
                    response: Response<List<Pokemon>>
                ) {
                    response.body()?.let {
                        pokemonResultCallback(it)
                    } ?: pokemonResultCallback(null)
                }

                override fun onFailure(call: Call<List<Pokemon>>, t: Throwable) {
                    pokemonResultCallback(null)
                }
            })
    }

    override fun getPokemon(id: String, pokemonResultCallback: (pokemon: Pokemon?) -> Unit) {
        ReactPokedexFactory.service.getPokemon(id).enqueue(object : Callback<Pokemon> {
            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                pokemonResultCallback(null)
            }

            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                pokemonResultCallback(response.body())
            }
        })
    }
}


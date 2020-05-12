package com.example.kotlinpokedex.data.repository

import android.util.Log
import com.example.kotlinpokedex.data.PokemonResult
import com.example.kotlinpokedex.data.ReactPokedexFactory
import com.example.kotlinpokedex.data.model.Pokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonApiDataSource : PokemonRepository {
    override fun getPokemons(offset: Int, pokemonResultCallback: (result: PokemonResult) -> Unit) {
        ReactPokedexFactory.service.getPokemons(offset.toString())
            .enqueue(object : Callback<List<Pokemon>> {
                override fun onResponse(
                    call: Call<List<Pokemon>>,
                    response: Response<List<Pokemon>>
                ) {
                    response.body()?.let {
                        pokemonResultCallback(PokemonResult.Success(it))
                    } ?: pokemonResultCallback(PokemonResult.Failed())
                }

                override fun onFailure(call: Call<List<Pokemon>>, t: Throwable) {
                    pokemonResultCallback(PokemonResult.Failed())
                }
            })
    }
}
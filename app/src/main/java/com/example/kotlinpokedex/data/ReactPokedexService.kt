package com.example.kotlinpokedex.data

import com.example.kotlinpokedex.data.model.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ReactPokedexService {
    @GET("/{offset}")
    fun getPokemons(@Path("offset") offset: String ) : Call<List<Pokemon>>

    @GET("/pokemon/{id}")
    fun getPokemon(@Path("id") id: String) : Call<Pokemon>
}
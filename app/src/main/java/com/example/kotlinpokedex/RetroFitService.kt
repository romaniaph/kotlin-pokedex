package com.example.kotlinpokedex

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RetroFitService {
    @GET("/{offset}")
    fun getPokemons(@Path("offset") offset: String ) : Call<List<Pokedex>>

    @GET("/pokemon/{id}")
    fun getPokemon(@Path("id") id: String) : Call<Pokedex>

}
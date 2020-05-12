package com.example.kotlinpokedex.data.repository

import com.example.kotlinpokedex.data.PokemonResult
import com.example.kotlinpokedex.data.model.Pokemon

interface PokemonRepository {
    fun getPokemons(offset: Int, pokemonResultCallback: (pokemons: List<Pokemon>?) -> Unit)
    fun getPokemon(id: String, pokemonResultCallback: (pokemon: Pokemon?) -> Unit)
}

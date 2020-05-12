package com.example.kotlinpokedex.data.repository

import com.example.kotlinpokedex.data.PokemonResult

interface PokemonRepository {
    fun getPokemons(offset: Int, pokemonResultCallback: (result: PokemonResult) -> Unit)
}

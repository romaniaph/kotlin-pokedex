package com.example.kotlinpokedex.data

import com.example.kotlinpokedex.data.model.Pokemon

//uma forma de enumerar as respostas/retornos da api
sealed class PokemonResult {
    class Success(val pokemons: List<Pokemon>) : PokemonResult()
    class Failed() : PokemonResult()
}
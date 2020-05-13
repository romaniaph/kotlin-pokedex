package com.example.kotlinpokedex.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.kotlinpokedex.data.PokemonResult
import com.example.kotlinpokedex.data.model.*
import com.example.kotlinpokedex.data.repository.PokemonRepository
import com.nhaarman.mockitokotlin2.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PokemonViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var liveDataPokemonList: Observer<List<Pokemon>>
    @Mock
    private lateinit var liveDataPokemon: Observer<Pokemon>

    private lateinit var viewModel: PokemonViewModel

    @Test
    fun `update liveDataPokemonList when getPokemons returns a pokemon list`() {
        //arrange
        val pokemons: List<Pokemon> = listOf(
            Pokemon(
                1,
                "Bulbasaur",
                listOf(
                    Ability("Abilidade 1"),
                    Ability("Abilidade 2")
                ),
                10.00,
                10.00,
                "https://img.pokemondb.net/sprites/x-y/shiny/bulbasaur.png",
                "https://img.pokemondb.net/sprites/x-y/shiny/bulbasaur.png",
                listOf(
                    Type("poison"),
                    Type("grass")
                ),
                listOf(
                    Game("Fire Red"),
                    Game("Leaf Green")
                ),
                listOf(
                    Move("Razor Leaf"),
                    Move("Cut")
                )
            ),
            Pokemon(
                1,
                "Bulbasaur 2",
                listOf(
                    Ability("Abilidade 1"),
                    Ability("Abilidade 2")
                ),
                10.00,
                10.00,
                "https://img.pokemondb.net/sprites/x-y/shiny/bulbasaur.png",
                "https://img.pokemondb.net/sprites/x-y/shiny/bulbasaur.png",
                listOf(
                    Type("poison"),
                    Type("grass")
                ),
                listOf(
                    Game("Fire Red"),
                    Game("Leaf Green")
                ),
                listOf(
                    Move("Razor Leaf"),
                    Move("Cut")
                )
            )
        )

        val resultSuccess = MockRepository(PokemonResult.GetPokemonsSuccess(pokemons))
        viewModel = PokemonViewModel(resultSuccess)
        viewModel.liveDataPokemonList.observeForever(liveDataPokemonList)

        //act
        viewModel.getPokemons()

        //assert
        verify(liveDataPokemonList).onChanged(pokemons)
    }

    @Test
    fun `update liveDataPokemon when getPokemon returns a pokemon`() {
        //arrange
        val pokemon: Pokemon = Pokemon(
            1,
            "Bulbasaur",
            listOf(
                Ability("Abilidade 1"),
                Ability("Abilidade 2")
            ),
            10.00,
            10.00,
            "https://img.pokemondb.net/sprites/x-y/shiny/bulbasaur.png",
            "https://img.pokemondb.net/sprites/x-y/shiny/bulbasaur.png",
            listOf(
                Type("poison"),
                Type("grass")
            ),
            listOf(
                Game("Fire Red"),
                Game("Leaf Green")
            ),
            listOf(
                Move("Razor Leaf"),
                Move("Cut")
            )
        )

        val resultSuccess = MockRepository(PokemonResult.GetPokemonSuccess(pokemon))
        viewModel = PokemonViewModel(resultSuccess)
        viewModel.liveDataPokemon.observeForever(liveDataPokemon)

        //act
        viewModel.getPokemon("1")

        //assert
        verify(liveDataPokemon).onChanged(pokemon)
    }
}

class MockRepository(private val result: PokemonResult) : PokemonRepository {
    override fun getPokemons(
        offset: Int,
        pokemonResultCallback: (pokemons: PokemonResult) -> Unit
    ) {
        pokemonResultCallback(result)
    }

    override fun getPokemon(id: String, pokemonResultCallback: (pokemon: PokemonResult) -> Unit) {
        pokemonResultCallback(result)
    }

}
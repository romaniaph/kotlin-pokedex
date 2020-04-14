package com.example.kotlinpokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {
    private var offsetGlobal: Int = 1
    private val pokemonArrayList = arrayListOf<Pokedex>()
    private val pokemonAdapter = PokemonListAdapter(pokemonArrayList)
    private var loading: Boolean = false
    private val api = RetroFitFactory().retrofitService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getPokemons(offsetGlobal)
        setScrollRecyclerView()

        search_button.setOnClickListener { searchPokemon() }
    }

    private fun searchPokemon() {
        val id = search_bar.text.toString().toLowerCase(Locale.getDefault()).trim()
        val intent: Intent = PokemonActivity.getIntent(this, id)
        startActivity(intent)
        search_bar.text.clear()
    }

    private fun setScrollRecyclerView() {
        pokemonList.addOnScrollStateChanged {
            if (!it.canScrollVertically(1)) {
                addPokemons(offsetGlobal)
            }
        }
    }

    private fun addPokemons(offset: Int) {
        progressBar.visibility = View.VISIBLE

        if (!loading) {
            loading = true
            api.getPokemons(offset.toString()).enqueue(object : Callback<List<Pokedex>> {

                override fun onFailure(call: Call<List<Pokedex>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Loading... Please, wait", Toast.LENGTH_LONG)
                        .show()

                    addPokemons(offsetGlobal)
                }

                override fun onResponse(
                    call: Call<List<Pokedex>>,
                    response: Response<List<Pokedex>>
                ) {
                    response.body()?.let {
                        pokemonArrayList.addAll(it)
                    }

                    Toast.makeText(this@MainActivity, "Loading more pokémons...", Toast.LENGTH_LONG)
                        .show()

                    offsetGlobal = offset + 21
                    pokemonAdapter.notifyDataSetChanged()
                    loading = false
                    progressBar.visibility = View.INVISIBLE
                }
            })
        }
    }

    private fun getPokemons(offset: Int) {
        progressBar.visibility = View.VISIBLE

        api.getPokemons(offset.toString()).enqueue(object : Callback<List<Pokedex>> {

            override fun onFailure(call: Call<List<Pokedex>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Loading... Please, wait", Toast.LENGTH_LONG).show()
                getPokemons(offsetGlobal)
            }

            override fun onResponse(call: Call<List<Pokedex>>, response: Response<List<Pokedex>>) {
                pokemonArrayList += response.body()!!
                setRecycler()
                offsetGlobal = offset + 21
            }
        })
    }


    private fun setRecycler() {
        progressBar.visibility = View.INVISIBLE

        pokemonList.apply {
            setHasFixedSize(true)

            //seta um gerenciador de layout
            //no caso, coloca como linear
            layoutManager = LinearLayoutManager(this@MainActivity)

            //seta um adaptador de layout
            //o adaptador que vai gerenciar as views la no recycler
            adapter = pokemonAdapter
        }
    }
}

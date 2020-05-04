package com.example.kotlinpokedex.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinpokedex.*
import com.example.kotlinpokedex.data.ReactPokedexFactory
import com.example.kotlinpokedex.data.addOnScrollStateChanged
import com.example.kotlinpokedex.data.model.Pokemon
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {
    private var offsetGlobal: Int = 1
    private lateinit var viewModel: PokemonViewModel
//    private lateinit var pokemonAdapter: PokemonAdapter
    private var loading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(PokemonViewModel::class.java)

        viewModel.liveData.observe(this, androidx.lifecycle.Observer {

            it?.let {
                with(pokemonList) {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = PokemonAdapter(it)
                }
            }
        })

        addPokemons()

        setScrollRecyclerView()
        search_button.setOnClickListener { searchPokemon() }
    }

    private fun searchPokemon() {
        val id = search_bar.text.toString().toLowerCase(Locale.getDefault()).trim()
        val intent: Intent =
            PokemonActivity.getIntent(
                this,
                id
            )
        startActivity(intent)
        search_bar.text.clear()
    }

    private fun setScrollRecyclerView() {
        pokemonList.addOnScrollStateChanged {
            if (!it.canScrollVertically(1)) {
                addPokemons()
            }
        }
    }

    private fun addPokemons() {
        progressBar.visibility = View.VISIBLE
        viewModel.getPokemons(offsetGlobal)
        offsetGlobal += 21
        progressBar.visibility = View.GONE

        pokemonList.adapter?.notifyDataSetChanged()
    }
}

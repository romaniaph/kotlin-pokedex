package com.example.kotlinpokedex.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinpokedex.*
import com.example.kotlinpokedex.data.addOnScrollStateChanged
import com.example.kotlinpokedex.data.model.Pokemon
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: PokemonViewModel
    private var pokemonList: ArrayList<Pokemon> = ArrayList()
    private val pokemonAdapter: PokemonAdapter = PokemonAdapter(pokemonList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(PokemonViewModel::class.java)

        viewModel.liveDataPokemonList.observe(this, androidx.lifecycle.Observer {
            it?.let {
                pokemonList.addAll(it)
                pokemonAdapter.notifyDataSetChanged()
            } ?: viewModel.getPokemons()
        })

        viewModel.liveDataLoading.observe(this, androidx.lifecycle.Observer {
            it?.let {
                if (!it)
                    progressBar.visibility = View.GONE
                else
                    progressBar.visibility = View.VISIBLE
            }
        })
        setRecyclerView()
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
        pokemonRecyclerView.addOnScrollStateChanged {
            if (!it.canScrollVertically(1)) {
                addPokemons()
            }
        }
    }

    private fun addPokemons() {
        viewModel.getPokemons()
    }

    private fun setRecyclerView() {
        with(pokemonRecyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = pokemonAdapter
        }
    }
}

package com.example.kotlinpokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_pokemon.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonActivity : AppCompatActivity() {
    val api = RetroFitFactory().retrofitService()
    lateinit var idPokemon: String
    var shiny: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)

        //pega o id do pokémon do bundle
        idPokemon = getIntent().getStringExtra("id")
        searchPokemon()
    }

    private fun searchPokemon() {
        api.getPokemon(idPokemon).enqueue(object : Callback<Pokedex> {
            override fun onFailure(call: Call<Pokedex>, t: Throwable) {
                notFound()
            }

            override fun onResponse(call: Call<Pokedex>, response: Response<Pokedex>) {
                if (response.body() != null) {
                    loadPokemon(response.body()!!)
                }
                else {
                    notFound()
                }
            }
        })
    }

    private fun notFound() {
        val imageNotFound: ImageView = findViewById(R.id.notFoundPikachu)
        imageNotFound.visibility = View.VISIBLE

        Glide.with(this)
            .load("https://cdn.lowgif.com/full/379453c527825283-pokemon-what-gif-find-share-on-giphy.gif")
            .into(imageNotFound)

        Toast.makeText(this, "Pokemon not found! Please, search again...", Toast.LENGTH_LONG).show()
        progressBarPokemon.visibility = View.GONE
    }

    private fun loadPokemon(pokemon: Pokedex) {

        namePokemon.text = "#${pokemon.id.toString()} ${pokemon.name.toString()}"
        width.text = "Weight: ${pokemon.weight.toString()}hg"
        height.text = "Height: ${pokemon.height.toString()}dm"

        val ImageNormal: ImageView = findViewById(R.id.imageNormal)

        ImageNormal.setOnClickListener {
            if(!shiny) {
                Glide.with(this).load(pokemon.imageshiny).into(ImageNormal)
            }
            else
            {
                Glide.with(this).load(pokemon.image).into(ImageNormal)
            }
            shiny = !shiny
        }

        val adapter = ArrayAdapter<String>(this, R.layout.list_item, pokemon.abilities.map { it.ability.toString() })

        val listView = findViewById<ListView>(R.id.abilities_list)
        listView.adapter = adapter

        Glide.with(this).load(pokemon.image).into(ImageNormal)
        ImageNormal.visibility = View.VISIBLE
        abilities.visibility = View.VISIBLE
        progressBarPokemon.visibility = View.GONE
    }
}

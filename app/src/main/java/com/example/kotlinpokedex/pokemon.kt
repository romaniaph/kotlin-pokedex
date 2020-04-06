package com.example.kotlinpokedex

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_pokemon.*
import kotlinx.android.synthetic.main.list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class pokemon : AppCompatActivity() {
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
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<Pokedex>, response: Response<Pokedex>) {
                loadPokemon(response.body()!!)
            }
        })
    }

    private fun loadPokemon(pokemon: Pokedex) {
        namePokemon.text = "#${pokemon.id.toString()} ${pokemon.name.toString()}"

        val ImageNormal: ImageView = findViewById(R.id.imageNormal)

        ImageNormal.setOnClickListener {
            if(!shiny) {
                Glide.with(this).load(pokemon.image).into(ImageNormal)
            }
        }

        Glide.with(this).load(pokemon.image).into(ImageNormal)
        ImageNormal.visibility = View.VISIBLE
        progressBarPokemon.visibility = View.GONE
    }
}

package com.example.kotlinpokedex

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_pokemon.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonActivity : AppCompatActivity() {
    private val api = RetroFitFactory().retrofitService()
    lateinit var idPokemon: String
    private var shiny: Boolean = false

    companion object {
        private const val ID_KEY = "id"

        fun getIntent(context: Context, id: String): Intent =
            Intent(context, PokemonActivity::class.java).apply {
                putExtra(ID_KEY, id)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon)

        //pega o id do pokémon do bundle
        idPokemon = getIntent().getStringExtra("id")

        if (idPokemon == "melo") {
            idPokemon = "54"
            Toast.makeText(this, "Salve melokkkkkkkk", Toast.LENGTH_LONG).show()
        }

        searchPokemon()
    }

    private fun searchPokemon() {
        api.getPokemon(idPokemon).enqueue(object : Callback<Pokedex> {
            override fun onFailure(call: Call<Pokedex>, t: Throwable) {
                notFound()
            }

            override fun onResponse(call: Call<Pokedex>, response: Response<Pokedex>) {
                if (response.body() != null)
                    response.body()?.let { loadPokemon(it) }
                else
                    notFound()
            }
        })
    }

    private fun notFound() {
        namePokemon.visibility = View.GONE
        val imageNotFound: ImageView = findViewById(R.id.notFoundPikachu)
        imageNotFound.visibility = View.VISIBLE

        Glide.with(this)
            .load("https://cdn.lowgif.com/full/379453c527825283-pokemon-what-gif-find-share-on-giphy.gif")
            .into(imageNotFound)

        Toast.makeText(this, "Pokemon not found! Please, search again...", Toast.LENGTH_LONG).show()
        progressBarPokemon.visibility = View.GONE
    }

    private fun setListViews(abilities: List<Ability>, moves: List<Move>) {
        val adapterAbilities =
            ArrayAdapter<String>(this, R.layout.list_item, abilities.map { it.ability.toString() })
        val listViewAbilities = findViewById<ListView>(R.id.abilities_list)
        listViewAbilities.adapter = adapterAbilities

        val adapterMoves =
            ArrayAdapter<String>(this, R.layout.list_item, moves.map { it.move.toString() })
        val listViewMoves = findViewById<ListView>(R.id.moves_list)
        listViewMoves.adapter = adapterMoves
    }

    private fun loadPokemon(pokemon: Pokedex) {

        namePokemon.text = "#${pokemon.id.toString()} ${pokemon.name.toString()}"
        width.text = "Weight: ${pokemon.weight.toString()}hg"
        height.text = "Height: ${pokemon.height.toString()}dm"

        val ImageNormal: ImageView = findViewById(R.id.imageNormal)

        ImageNormal.setOnClickListener {
            if (!shiny) {
                Glide.with(this).load(pokemon.imageshiny).into(ImageNormal)
            } else {
                Glide.with(this).load(pokemon.image).into(ImageNormal)
            }
            shiny = !shiny
        }

        list_type.apply {
            setHasFixedSize(true)
            adapter = TypeListAdapter(pokemon.types)
            layoutManager =
                LinearLayoutManager(this@PokemonActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        setListViews(pokemon.abilities, pokemon.moves)

        Glide.with(this).load(pokemon.image).into(ImageNormal)
        ImageNormal.visibility = View.VISIBLE
        abilities.visibility = View.VISIBLE
        moves.visibility = View.VISIBLE
        progressBarPokemon.visibility = View.GONE
    }
}

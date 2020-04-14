package com.example.kotlinpokedex

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_pokemon.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonActivity : AppCompatActivity() {
    private val api = RetroFitFactory().retrofitService()
    private lateinit var idPokemon: String

    private lateinit var listViewMoves: View
    private var heightMoves: Int = 0
    private var expandedMoves: Boolean = false

    private lateinit var listViewGames: View
    private var heightGames: Int = 0
    private var expandedGames: Boolean = false

    private var shiny: Boolean = false

    //converte int pra DP (pra ajustar a altura do listview)
    private val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

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

        easterEgg()
        searchPokemon()
        setClickListeners()
    }

    private fun easterEgg() {
        if (idPokemon == "melo") {
            idPokemon = "54"
            Toast.makeText(this, "Salve melokkkkkkkk", Toast.LENGTH_LONG).show()
        }
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

    private fun setListViews(abilities: List<Ability>, moves: List<Move>, games: List<Game>) {

        //seta os adaptadores dos listviews

        val adapterAbilities: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.list_item, abilities.map { it.ability })
        val listViewAbilities = findViewById<ListView>(R.id.abilities_list)
        listViewAbilities.adapter = adapterAbilities

        val adapterMoves: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.list_item, moves.map { it.move })
        val listViewMoves = findViewById<ListView>(R.id.moves_list)
        listViewMoves.adapter = adapterMoves

        val adapterGames: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.list_item, games.map { it.game })
        val listViewGames = findViewById<ListView>(R.id.games_list)
        listViewGames.adapter = adapterGames

        // os maps abaixo setam a altura dos listviews (mostrar todos os items)

        abilities.map {
            listViewAbilities.layoutParams.height += 36.dp
        }

        moves.map {
            heightMoves += 36.dp
        }

        games.map {
            heightGames += 36.dp
        }
    }

    private fun loadPokemon(pokemon: Pokedex) {
        idPokemon = pokemon.id.toString()

        namePokemon.text = "#${pokemon.id.toString()} ${pokemon.name.toString()}"
        width.text = "Weight: ${pokemon.weight.toString()}hg"
        height.text = "Height: ${pokemon.height.toString()}dm"

        val image: ImageView = findViewById(R.id.imageNormal)
        Glide.with(this).load(pokemon.image).into(image)
        image.visibility = View.VISIBLE

        image.setOnClickListener {
            if (!shiny) {
                Glide.with(this).load(pokemon.imageshiny).into(image)
            } else {
                Glide.with(this).load(pokemon.image).into(image)
            }
            shiny = !shiny
        }

        list_type.apply {
            setHasFixedSize(true)
            adapter = TypeListAdapter(pokemon.types)
            layoutManager =
                LinearLayoutManager(this@PokemonActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        setListViews(pokemon.abilities, pokemon.moves, pokemon.games)

        showAll()
    }

    private fun showAll() {
        abilities.visibility = View.VISIBLE

        moves.visibility = View.VISIBLE
        expand_moves.visibility = View.VISIBLE

        games.visibility = View.VISIBLE
        expand_games.visibility = View.VISIBLE

        progressBarPokemon.visibility = View.GONE

        next_button.visibility = View.VISIBLE

        if (idPokemon != "1")
            previous_buttton.visibility = View.VISIBLE
    }

    private fun expandMoves() {
        listViewMoves = findViewById<ListView>(R.id.moves_list)
        val lp = listViewMoves.layoutParams

        if (expandedMoves) {
            lp.height = 0.dp
            listViewMoves.layoutParams = lp
            expand_moves.setImageResource(R.drawable.ic_expand_more)
        } else {
            lp.height = heightMoves
            listViewMoves.layoutParams = lp
            expand_moves.setImageResource(R.drawable.ic_expand_less)
        }

        expandedMoves = !expandedMoves
    }

    private fun expandGames() {
        listViewGames = findViewById(R.id.games_list)
        val lp = listViewGames.layoutParams

        if (expandedGames) {
            lp.height = 0.dp
            listViewGames.layoutParams = lp
            expand_games.setImageResource(R.drawable.ic_expand_more)
        } else {
            lp.height = heightGames
            listViewGames.layoutParams = lp
            expand_games.setImageResource(R.drawable.ic_expand_less)
        }

        expandedGames = !expandedGames
    }

    private fun nextPokemon() {
        val next: Int = idPokemon.toInt() + 1

        val intent: Intent = PokemonActivity.getIntent(this, next.toString())
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        this.finish()
    }

    private fun previousPokemon() {
        val next: Int = idPokemon.toInt() - 1

        val intent: Intent = PokemonActivity.getIntent(this, next.toString())
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        this.finish()
    }

    private fun setClickListeners() {
        expand_moves.setOnClickListener {
            expandMoves()
        }

        expand_games.setOnClickListener {
            expandGames()
        }

        next_button.setOnClickListener {
            nextPokemon()
        }

        previous_buttton.setOnClickListener {
            previousPokemon()
        }
    }

}

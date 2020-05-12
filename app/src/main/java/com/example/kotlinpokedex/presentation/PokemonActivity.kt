package com.example.kotlinpokedex.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kotlinpokedex.*
import com.example.kotlinpokedex.data.dp
import com.example.kotlinpokedex.data.model.*
import com.example.kotlinpokedex.data.repository.PokemonApiDataSource
import kotlinx.android.synthetic.main.activity_pokemon.*

class PokemonActivity : AppCompatActivity() {
    private lateinit var viewModel: PokemonViewModel

    private lateinit var idPokemon: String

    private lateinit var listViewMoves: View
    private var heightMoves: Int = 0
    private var expandedMoves: Boolean = false

    private lateinit var listViewGames: View
    private var heightGames: Int = 0
    private var expandedGames: Boolean = false

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

        viewModel = PokemonViewModel.ViewModelFactory(PokemonApiDataSource()).create(PokemonViewModel::class.java)

        viewModel.liveDataLoading.observe(this, Observer {
            it?.let {
                if (!it) progressBarPokemon.visibility =
                    View.INVISIBLE else progressBarPokemon.visibility = View.VISIBLE
            }
        })

        viewModel.liveDataPokemon.observe(this, Observer {
            it?.let {
                loadPokemon(it)
            } ?: notFound()
        })

        //pega o id do pokémon do bundle
        idPokemon = intent.getStringExtra("id")!!
        viewModel.getPokemon(idPokemon)
        easterEgg()
        setClickListeners()
    }

    private fun easterEgg() {
        if (idPokemon == "melo") {
            idPokemon = "54"
            Toast.makeText(this, "Salve melokkkkkkkk", Toast.LENGTH_LONG).show()
        }
    }

    private fun notFound() {
        Log.d("not found", "not found")

        namePokemon.visibility = View.GONE
        val imageNotFound: ImageView = findViewById(R.id.notFoundPikachu)
        imageNotFound.visibility = View.VISIBLE

        Glide.with(this)
            .load("https://cdn.lowgif.com/full/379453c527825283-pokemon-what-gif-find-share-on-giphy.gif")
            .into(imageNotFound)

        Toast.makeText(this, "Pokemon not found! Please, search again...", Toast.LENGTH_LONG).show()
    }

    private fun setListView(listViewId: Int, contentList: List<String>) {
        val adapter: ArrayAdapter<Any> = ArrayAdapter(
            this,
            R.layout.list_item, contentList
        )

        val listView: ListView = findViewById(listViewId)
        listView.adapter = adapter
    }

    private fun setRecyclerView(types: List<Type>) {
        list_type.apply {
            setHasFixedSize(true)
            adapter = TypeAdapter(types)
            layoutManager = LinearLayoutManager(this@PokemonActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setHeights(abilities: List<Ability>, moves: List<Move>, games: List<Game>) {

        // os maps abaixo setam a altura dos listviews (mostrar todos os items)
        abilities.map {
            abilities_list.layoutParams.height += 36.dp
        }

        moves.map {
            heightMoves += 36.dp
        }

        games.map {
            heightGames += 36.dp
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadPokemon(pokemon: Pokemon) {
        idPokemon = pokemon.id.toString()

        namePokemon.text = "#${pokemon.id} ${pokemon.name}"
        weight.text = "Weight: ${pokemon.weight}hg"
        height.text = "Height: ${pokemon.height}dm"

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

        setRecyclerView(pokemon.types)

        //seta o conteúdo das listviews
        setListView(R.id.games_list, pokemon.games.map { it.game })
        setListView(R.id.moves_list, pokemon.moves.map { it.move })
        setListView(R.id.abilities_list, pokemon.abilities.map { it.ability })

        //seta a altura que cada uma deve ter -> tem que fazer isso manualmente por conta do scroll
        //isso também é feito pra lógica de expandir e esconder uma lista
        setHeights(pokemon.abilities, pokemon.moves, pokemon.games)

        //exibe o que for necessário
        showAll()
    }

    private fun showAll() {
        abilities.visibility = View.VISIBLE

        moves.visibility = View.VISIBLE
        expand_moves.visibility = View.VISIBLE

        games.visibility = View.VISIBLE
        expand_games.visibility = View.VISIBLE

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

        val intent: Intent =
            getIntent(
                this,
                next.toString()
            )
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        this.finish()
    }

    private fun previousPokemon() {
        val next: Int = idPokemon.toInt() - 1

        val intent: Intent =
            getIntent(
                this,
                next.toString()
            )
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

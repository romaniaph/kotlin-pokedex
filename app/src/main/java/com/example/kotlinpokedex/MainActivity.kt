package com.example.kotlinpokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var offsetGlobal: Int = 1
    private val pokemonArrayList = arrayListOf<Pokedex>()
    val pokemonAdapter = PokemonListAdapter(pokemonArrayList)
    var loading: Boolean = false
    val api = RetroFitFactory().retrofitService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getPokemons(offsetGlobal)
        setScrollRecyclerView()
    }

    fun searchPokemon(view: View) {
        val bundle: Bundle = Bundle()
        bundle.putString("id", search_bar.text.toString().toLowerCase().trim())
        val intent: Intent = Intent(this,  PokemonActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
        search_bar.text.clear()
    }

    private fun setScrollRecyclerView() {
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    addPokemons(offsetGlobal)
                }
            }
        }

        pokemonList.addOnScrollListener(scrollListener)

    }

    private fun addPokemons(offset: Int) {
        progressBar.visibility = View.VISIBLE

        if (!loading) {
            loading = true
            api.getPokemons(offset.toString()).enqueue(object : Callback<List<Pokedex>> {

                override fun onFailure(call: Call<List<Pokedex>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Loading... Please, wait", Toast.LENGTH_LONG)
                        .show()
                    progressBar.visibility = View.INVISIBLE
                    loading = false
                }

                override fun onResponse(
                    call: Call<List<Pokedex>>,
                    response: Response<List<Pokedex>>
                ) {
                    pokemonArrayList!!.addAll(response.body()!!)
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
                d("n foi", t.toString())
            }

            override fun onResponse(call: Call<List<Pokedex>>, response: Response<List<Pokedex>>) {
                var start = pokemonArrayList.size
                pokemonArrayList += response.body()!!
                setRecycler(response.body()!!, start, pokemonArrayList.size)
                offsetGlobal = offset + 21
            }
        })
    }


    private fun setRecycler(pokemon: List<Pokedex>, start: Int, count: Int) {
        progressBar.visibility = View.INVISIBLE

        pokemonList.apply {
            setHasFixedSize(true)

            //seta um gerenciador de layout
            //no caso, coloca como lineat
            layoutManager = LinearLayoutManager(this@MainActivity)

            //seta um adaptador de layout
            //o adaptador que vai gerenciar as views la no recycler
            adapter = pokemonAdapter
        }
    }
}

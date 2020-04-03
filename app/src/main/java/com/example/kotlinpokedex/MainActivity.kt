package com.example.kotlinpokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var offsetGlobal: Int = 1
    private val pokemonArrayList = arrayListOf<Pokedex>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getPokemons(offsetGlobal)

        floating_button.setOnClickListener {
            getPokemons(offsetGlobal)
        }
    }

    private fun getPokemons(offset: Int) {
        val api = RetroFitFactory().retrofitService()
        progressBar.visibility = View.VISIBLE

        api.getPokemons(offset.toString()).enqueue(object : Callback<List<Pokedex>> {

            override fun onFailure(call: Call<List<Pokedex>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "PORRRAAA", Toast.LENGTH_LONG).show()
                d("n foi", t.toString())
            }

            override fun onResponse(call: Call<List<Pokedex>>, response: Response<List<Pokedex>>) {
                pokemonArrayList += response.body()!!
                setRecycler(pokemonArrayList)

                offsetGlobal = offset + 20
            }
        })
    }


    private fun setRecycler(pokemon: List<Pokedex>) {

        pokemonList.apply {
            setHasFixedSize(true)

            progressBar.visibility = View.INVISIBLE

            //seta um gerenciador de layout
            //no caso, coloca como lineat
            layoutManager = LinearLayoutManager(this@MainActivity)

            //seta um adaptador de layout
            //o adaptador que vai gerenciar as views la no recycler
            adapter = PokemonListAdapter(pokemon)
        }
    }
}

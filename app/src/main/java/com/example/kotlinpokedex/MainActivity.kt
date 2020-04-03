package com.example.kotlinpokedex

import android.content.Context
import android.opengl.Visibility
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

    private lateinit var reciclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//                    Glide.with(this@MainActivity).load(Pokemon?.image.toString()).into(imageView)

        val api = RetroFitFactory().retrofitService()

        val pokemons = api.getPokemons("1").enqueue(object : Callback<List<Pokedex>> {

            override fun onFailure(call: Call<List<Pokedex>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "PORRRAAA", Toast.LENGTH_LONG).show()
                d("n foi", t.toString())
            }

            override fun onResponse(call: Call<List<Pokedex>>, response: Response<List<Pokedex>>) {
                var progress = this@MainActivity.findViewById<ProgressBar>(R.id.progressBar)

                progress.isIndeterminate = false
                setRecycler(response.body()!!)
                progress.visibility = View.GONE
            }
        })
    }


    fun setRecycler(pokemons: List<Pokedex>) {


        pokemonList.apply {
            setHasFixedSize(true)

            //seta um gerenciador de layout
            layoutManager = LinearLayoutManager(this@MainActivity)

            //seta um adaptador de layout
            adapter = PokemonListAdapter(pokemons)
        }
    }
}

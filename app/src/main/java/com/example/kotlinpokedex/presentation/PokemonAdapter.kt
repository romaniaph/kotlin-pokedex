package com.example.kotlinpokedex.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinpokedex.data.model.Pokemon
import com.example.kotlinpokedex.R
import kotlinx.android.synthetic.main.list_item_pokemon.view.*
import java.util.*

// a classe recebe a lista de pokémons e extende o adapter do RecyclerView
class PokemonAdapter(private var pokemons: List<Pokemon>) :
    RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //manipulador de pokémon -> ids do card
        @SuppressLint("SetTextI18n")
        fun bind(pokemon: Pokemon) {
            itemView.name.text = pokemon.name.toString().toUpperCase(Locale.ROOT)
            itemView.idtext.text = "#${pokemon.id.toString()}"

            Glide.with(itemView).load(pokemon?.image.toString()).into(itemView.image)
            val context = itemView.context

            itemView.setOnClickListener {
                val bundle: Bundle = Bundle()
                bundle.putString("id", pokemon.id.toString())
                val intent: Intent = Intent(context,  PokemonActivity::class.java)
                intent.putExtras(bundle)
                context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //infla com o carditem criado
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_pokemon, parent, false)

        //retorna essa view
        return ViewHolder(view)
    }

    // saber quantos itens a lista vai renderizar
    override fun getItemCount(): Int = pokemons.size

    //quando tiver rolagem saber qual elemento buscar
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pokemons[position])
    }
}
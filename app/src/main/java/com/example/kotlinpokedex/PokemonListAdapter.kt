package com.example.kotlinpokedex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item.view.*


class PokemonListAdapter(private val pokemons: List<Pokedex>) :
    RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //manipulador de pokémon -> ids do card
        fun bind(pokemon: Pokedex) {
            itemView.name.text = pokemon.name.toString()
            itemView.idtext.text = "#${pokemon.id.toString()}"

            Glide.with(itemView).load(pokemon?.image.toString()).into(itemView.image)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //infla com o carditem criado
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

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
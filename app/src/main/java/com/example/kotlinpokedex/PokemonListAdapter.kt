package com.example.kotlinpokedex

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item_pokemon.view.*

// a classe recebe a lista de pokémons e extende o adapter do RecyclerView
class PokemonListAdapter(private var pokemons: List<Pokedex>) :
    RecyclerView.Adapter<PokemonListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //manipulador de pokémon -> ids do card
        fun bind(pokemon: Pokedex) {
            itemView.name.text = pokemon.name.toString().toUpperCase()
            itemView.idtext.text = "#${pokemon.id.toString()}"

            Glide.with(itemView).load(pokemon?.image.toString()).into(itemView.image)
            val context = itemView.context

            itemView.setOnClickListener {
                val bundle: Bundle = Bundle()
                bundle.putString("id", pokemon.id.toString())
                val intent: Intent = Intent(context,  com.example.kotlinpokedex.PokemonActivity::class.java)
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
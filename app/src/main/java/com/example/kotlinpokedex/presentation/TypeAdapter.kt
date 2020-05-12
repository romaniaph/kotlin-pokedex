package com.example.kotlinpokedex.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpokedex.R
import com.example.kotlinpokedex.data.model.Type
import kotlinx.android.synthetic.main.list_item_type.view.*

class TypeAdapter(private var types: List<Type>) :
    RecyclerView.Adapter<TypeAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private fun setBackground(id: Int) {
            itemView.type_textview.setBackgroundResource(id)
        }

        fun bind(type: Type) {
            itemView.type_textview.text = type.type

            when (type.type) {
                "poison" -> setBackground(R.drawable.type_poison)
                "water" -> setBackground(R.drawable.type_water)
                "rock" -> setBackground(R.drawable.type_rock)
                "psychic" -> setBackground(R.drawable.type_psychic)
                "normal" -> setBackground(R.drawable.type_normal)
                "ice" -> setBackground(R.drawable.type_ice)
                "ground" -> setBackground(R.drawable.type_ground)
                "grass" -> setBackground(R.drawable.type_grass)
                "ghost" -> setBackground(R.drawable.type_ghost)
                "flying" -> setBackground(R.drawable.type_flying)
                "fire" -> setBackground(R.drawable.type_fire)
                "fighting" -> setBackground(R.drawable.type_fighting)
                "fairy" -> setBackground(R.drawable.type_fairy)
                "electric" -> setBackground(R.drawable.type_electric)
                "dragon" -> setBackground(R.drawable.type_dragon)
                "dark" -> setBackground(R.drawable.type_dark)
                "bug" -> setBackground(R.drawable.type_bug)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        //infla com o carditem criado
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_type, parent, false)

        //retorna essa view
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = types.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(types[position])
    }
}

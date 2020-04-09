package com.example.kotlinpokedex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_type.view.*

class TypeListAdapter(private var types: List<Type>) :
    RecyclerView.Adapter<TypeListAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(type: Type) {
            itemView.type_textview.text = type.type.toString()

            when (type.type.toString()) {
                "poison" -> itemView.type_textview.setBackgroundResource(R.drawable.type_poison)
                "water" -> itemView.type_textview.setBackgroundResource(R.drawable.type_water)
                "rock" -> itemView.type_textview.setBackgroundResource(R.drawable.type_rock)
                "psychic" -> itemView.type_textview.setBackgroundResource(R.drawable.type_psychic)
                "normal" -> itemView.type_textview.setBackgroundResource(R.drawable.type_normal)
                "ice" -> itemView.type_textview.setBackgroundResource(R.drawable.type_ice)
                "ground" -> itemView.type_textview.setBackgroundResource(R.drawable.type_ground)
                "grass" -> itemView.type_textview.setBackgroundResource(R.drawable.type_grass)
                "ghost" -> itemView.type_textview.setBackgroundResource(R.drawable.type_ghost)
                "flying" -> itemView.type_textview.setBackgroundResource(R.drawable.type_flying)
                "fire" -> itemView.type_textview.setBackgroundResource(R.drawable.type_fire)
                "fighting" -> itemView.type_textview.setBackgroundResource(R.drawable.type_fighting)
                "fairy" -> itemView.type_textview.setBackgroundResource(R.drawable.type_fairy)
                "fire" -> itemView.type_textview.setBackgroundResource(R.drawable.type_fire)
                "eletric" -> itemView.type_textview.setBackgroundResource(R.drawable.type_eletric)
                "dragon" -> itemView.type_textview.setBackgroundResource(R.drawable.type_dragon)
                "dark" -> itemView.type_textview.setBackgroundResource(R.drawable.type_dark)
                "bug" -> itemView.type_textview.setBackgroundResource(R.drawable.type_bug)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TypeListAdapter.ViewHolder {
        //infla com o carditem criado
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_type, parent, false)

        //retorna essa view
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = types.size


    override fun onBindViewHolder(holder: TypeListAdapter.ViewHolder, position: Int) {
        holder.bind(types[position])
    }
}
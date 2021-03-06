package com.example.kotlinpokedex.data.model

import com.google.gson.annotations.SerializedName

data class Pokemon (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("abilities") val abilities: List<Ability>,
    @SerializedName("height") val height: Double,
    @SerializedName("weight") val weight: Double,
    @SerializedName("image") val image: String,
    @SerializedName("imageShiny") val imageShiny: String,
    @SerializedName("types") val types: List<Type>,
    @SerializedName("games") val games: List<Game>,
    @SerializedName("moves") val moves: List<Move>
)
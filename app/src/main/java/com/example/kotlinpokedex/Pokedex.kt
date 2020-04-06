package com.example.kotlinpokedex

import com.google.gson.annotations.SerializedName

data class Pokedex (
    @SerializedName("id") val id: Int ,
    @SerializedName("name") val name: String,
    @SerializedName("abilities") val abilities: List<Any>,
    @SerializedName("height") val height: Double,
    @SerializedName("width") val width: Double,
    @SerializedName("image") val image: String,
    @SerializedName("imageShiny") val imageShiny: String,
    @SerializedName("imageshiny") val imageshiny: String,
    @SerializedName("types") val types: List<Any>,
    @SerializedName("games") val games: List<Any>,
    @SerializedName("moves") val moves: List<Any>
)
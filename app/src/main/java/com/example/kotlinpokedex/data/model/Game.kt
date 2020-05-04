package com.example.kotlinpokedex.data.model

import com.google.gson.annotations.SerializedName

data class Game (
    @SerializedName("game") val game: String
)
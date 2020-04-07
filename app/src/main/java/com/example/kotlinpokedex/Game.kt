package com.example.kotlinpokedex

import com.google.gson.annotations.SerializedName

data class Game (
    @SerializedName("game") val game: String
)
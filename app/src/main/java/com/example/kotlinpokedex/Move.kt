package com.example.kotlinpokedex

import com.google.gson.annotations.SerializedName

data class Move (
    @SerializedName("move") val move: String
)
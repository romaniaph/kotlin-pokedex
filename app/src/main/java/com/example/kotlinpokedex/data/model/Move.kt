package com.example.kotlinpokedex.data.model

import com.google.gson.annotations.SerializedName

data class Move (
    @SerializedName("move") val move: String
)
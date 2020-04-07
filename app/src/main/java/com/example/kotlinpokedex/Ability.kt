package com.example.kotlinpokedex

import com.google.gson.annotations.SerializedName

data class Ability(
    @SerializedName("ability") val ability: String
)
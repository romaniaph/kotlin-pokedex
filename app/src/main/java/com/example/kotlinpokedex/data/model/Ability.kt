package com.example.kotlinpokedex.data.model

import com.google.gson.annotations.SerializedName

data class Ability(
    @SerializedName("ability") val ability: String
)
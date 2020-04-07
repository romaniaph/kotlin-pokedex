package com.example.kotlinpokedex

import com.google.gson.annotations.SerializedName

data class Type (
    @SerializedName("type") val type: String
)
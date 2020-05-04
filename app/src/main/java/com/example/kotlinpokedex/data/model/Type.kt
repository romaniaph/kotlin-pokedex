package com.example.kotlinpokedex.data.model

import com.google.gson.annotations.SerializedName

data class Type (
    @SerializedName("type") val type: String
)
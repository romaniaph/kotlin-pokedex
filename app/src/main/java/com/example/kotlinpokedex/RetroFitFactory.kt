package com.example.kotlinpokedex

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//criador da api, de fato

class RetroFitFactory {
    val URL: String = "https://react-pokedex-romaniaph.herokuapp.com"

    val retrofitFactory = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //cria o serviço juntando com a classe RetroFitService (que tem os métodos e endpoints já predefinidos)
    fun retrofitService(): RetroFitService = retrofitFactory.create(RetroFitService::class.java)

}
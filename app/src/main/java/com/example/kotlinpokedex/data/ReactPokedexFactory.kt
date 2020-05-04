package com.example.kotlinpokedex.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//criador da api, de fato
object ReactPokedexFactory {
    private val URL: String = "https://react-pokedex-romaniaph.herokuapp.com"

    //configurações
    private fun initRetrofit() : Retrofit {
        return Retrofit.Builder() //construtor
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create()) //convertor pra Gson
            .build()
    }

    //cria o serviço juntando com a classe RetroFitService (que tem os métodos e endpoints já predefinidos)
    val service = initRetrofit().create(ReactPokedexService::class.java)

}
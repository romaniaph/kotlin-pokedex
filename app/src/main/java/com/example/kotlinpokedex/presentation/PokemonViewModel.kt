package com.example.kotlinpokedex.presentation

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinpokedex.data.ReactPokedexFactory
import com.example.kotlinpokedex.data.model.Pokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonViewModel : ViewModel() {
    val liveData: MutableLiveData<List<Pokemon>> = MutableLiveData();

    fun getPokemons(offset: Int) {
        ReactPokedexFactory.service.getPokemons(offset.toString())
            .enqueue(object : Callback<List<Pokemon>> {
                override fun onResponse(
                    call: Call<List<Pokemon>>,
                    response: Response<List<Pokemon>>
                ) {
                    if (response.isSuccessful) {
                        val mutableList = mutableListOf<Pokemon>()

                        liveData.value?.let {
                            mutableList.addAll(it.toMutableList())
                        }
                        mutableList.addAll(response.body()!!)
                        liveData.value = mutableList
                    }
                }

                override fun onFailure(call: Call<List<Pokemon>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }

}

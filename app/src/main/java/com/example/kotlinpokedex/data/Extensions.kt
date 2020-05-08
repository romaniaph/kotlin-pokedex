package com.example.kotlinpokedex.data

import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView

//extensão pro RecyclerView que recebe uma função
fun RecyclerView.addOnScrollStateChanged(function: (recyclerView: RecyclerView) -> Unit) {

    //adiciona a função no OnScrollListener() do recyclerview
    addOnScrollListener(
        object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                function(recyclerView)
            }
        }
    )
}

//converte int pra DP (pra ajustar a altura do listview)
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

package com.example.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://655e87bc879575426b439d7e.mockapi.io/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val consumirapi = retrofit.create(Consumirapi::class.java)
}


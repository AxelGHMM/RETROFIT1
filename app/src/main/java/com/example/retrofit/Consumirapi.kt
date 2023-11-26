package com.example.retrofit

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface Consumirapi {
        @POST("programmers")
        fun addProgrammer(@Body programmer: jsonplace): Call<jsonplace>

        @GET("programmers/{id}")
        fun getProgrammer(@Path("id") id: Int): Call<jsonplace>

        @PUT("programmers/{id}")
        fun updateProgrammer(@Path("id") id: Int, @Body programmer: jsonplace): Call<jsonplace>

        @DELETE("programmers/{id}")
        fun deleteProgrammer(@Path("id") id: Int): Call<Void>
    }


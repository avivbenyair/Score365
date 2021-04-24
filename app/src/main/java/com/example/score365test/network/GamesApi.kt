package com.example.score365test.network

import com.example.score365test.network.response.GamesDataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface GamesApi {

    @GET("Data/Games/")
    fun retrieveGamesData(@Query("lang") lang:Int, @Query("uc") uc:Int, @Query("tz") tz:Int, @Query("countries") countries:Int, @Query("uid") lastUpdateID: Long = 0): Call<GamesDataResponse>
}
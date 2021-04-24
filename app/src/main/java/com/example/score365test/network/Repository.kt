package com.example.score365test.network

import com.example.score365test.network.response.GamesDataResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://mobilews.365scores.com/"
const val LANGUAGE = 1
const val UC = 6
const val TZ = 15
const val COUNTRIES = 1

object Repository {

    fun retrieveGamesData(callback: Callback<GamesDataResponse>) {
        val gamesApi: GamesApi = mRetrofit.create(GamesApi::class.java)
        val call: Call<GamesDataResponse> = gamesApi.retrieveGamesData(LANGUAGE, UC, TZ, COUNTRIES)
        call.enqueue(callback)
    }

    fun reRetrieveGamesData(LastUpdateID: String, callback: Callback<GamesDataResponse>) {
        val gamesApi: GamesApi = mRetrofit.create(GamesApi::class.java)
        val call: Call<GamesDataResponse> =
            gamesApi.retrieveGamesData(LANGUAGE, UC, TZ, COUNTRIES, lastUpdateID = LastUpdateID.toLong())
        call.enqueue(callback)
    }

    private val mGson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private val mRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getHttpClient())
        .addConverterFactory(GsonConverterFactory.create(mGson))
        .build()

    private fun getHttpClient(): OkHttpClient? {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return httpClient.build()
    }
}
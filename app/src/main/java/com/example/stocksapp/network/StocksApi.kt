package com.example.stocksapp.network

import com.example.stocksapp.model.TopGainersandLosers.TopGainersandLosers
import com.example.stocksapp.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface StocksApi {
    @GET("query")
    suspend fun getTopGainersandLosers(@Query("function") function: String,
                                       @Query("apikey") apiKey: String=API_KEY): TopGainersandLosers

}
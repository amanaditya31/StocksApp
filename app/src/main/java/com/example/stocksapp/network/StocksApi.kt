package com.example.stocksapp.network

import com.example.stocksapp.model.TopGainersandLosers.TopGainersandLosers
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface StocksApi {
    @GET
    suspend fun getTopGainersandLosers(@Query("function") query: String): TopGainersandLosers
}
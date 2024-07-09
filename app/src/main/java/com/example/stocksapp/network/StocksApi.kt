package com.example.stocksapp.network

import com.example.stocksapp.model.CompanyOverview.CompanyOverview
import com.example.stocksapp.model.TickerSearch.BestMatche
import com.example.stocksapp.model.TickerSearch.TickerSearch
import com.example.stocksapp.model.TopGainersandLosers.TopGainersandLosers
import com.example.stocksapp.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface StocksApi {
    @GET("query")
    suspend fun getTopGainersandLosers(@Query("function") function: String="TOP_GAINERS_LOSERS",
                                       @Query("apikey") apiKey: String=API_KEY): TopGainersandLosers

    @GET("query")
    suspend fun getCompanyOverview(@Query("function") function: String="OVERVIEW",
                                   @Query("symbol") symbol: String ,
                                   @Query("apikey") apiKey: String=API_KEY): CompanyOverview

    @GET("query")
    suspend fun getSearches(@Query("function") function: String="SYMBOL_SEARCH",
                                   @Query("keywords") keywords: String ,
                                   @Query("apikey") apiKey: String=API_KEY): TickerSearch

}
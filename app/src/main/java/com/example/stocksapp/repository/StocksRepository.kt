package com.example.stocksapp.repository

import com.example.stocksapp.data.Resource
import com.example.stocksapp.model.TopGainersandLosers.TopGainer
import com.example.stocksapp.model.TopGainersandLosers.TopLoser
import com.example.stocksapp.network.StocksApi
import retrofit2.http.Query
import javax.inject.Inject

class StocksRepository @Inject constructor(private val api: StocksApi) {

    suspend fun getTopGainerStocks( searchQuery: String) : Resource<List<TopGainer>>{
        return try{
            Resource.Loading(data=true)
            val topGainerList=api.getTopGainersandLosers(searchQuery).top_gainers
            if(topGainerList.isNotEmpty()) Resource.Loading(data=false)
            Resource.Success(data=topGainerList)
        }catch(exception: Exception){
            Resource.Error(message = exception.message.toString())
        }
    }

    suspend fun getTopLoserStocks( searchQuery: String) : Resource<List<TopLoser>>{
        return try{
            Resource.Loading(data=true)
            val topLoserList=api.getTopGainersandLosers(searchQuery).top_losers
            if(topLoserList.isNotEmpty()) Resource.Loading(data=false)
            Resource.Success(data=topLoserList)
        }catch(exception: Exception){
            Resource.Error(message = exception.message.toString())
        }
    }
}
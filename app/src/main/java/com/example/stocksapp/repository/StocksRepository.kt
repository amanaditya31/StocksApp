package com.example.stocksapp.repository

import com.example.stocksapp.data.Resource
import com.example.stocksapp.model.CompanyOverview.CompanyOverview
import com.example.stocksapp.model.TickerSearch.BestMatche
import com.example.stocksapp.model.TopGainersandLosers.TopGainer
import com.example.stocksapp.model.TopGainersandLosers.TopGainersandLosers
import com.example.stocksapp.model.TopGainersandLosers.TopLoser
import com.example.stocksapp.network.StocksApi
import javax.inject.Inject

class StocksRepository @Inject constructor(private val api: StocksApi) {

    suspend fun getStocks( searchQuery: String) : Resource<TopGainersandLosers>{
        return try{
            Resource.Loading(data=true)
            val topGainersandLosersStocks=api.getTopGainersandLosers(searchQuery)
            if(topGainersandLosersStocks.metadata.isNotBlank()) Resource.Loading(data=false)
            Resource.Success(data=topGainersandLosersStocks)
        }catch(exception: Exception){
            Resource.Error(message = exception.message.toString())
        }
    }
    suspend fun getCompanyOverviewStock( searchQuery: String) : Resource<CompanyOverview>{
        return try{
            Resource.Loading(data=true)
            val overview=api.getCompanyOverview(symbol=searchQuery)
            if(overview.Symbol.isNotBlank()) Resource.Loading(data=false)
            Resource.Success(data=overview)
        }catch(exception: Exception){
            Resource.Error(message = exception.message.toString())
        }
    }


    suspend fun getBestMatches( searchQuery: String) : Resource<List<BestMatche>>{
        return try{
            Resource.Loading(data=true)
            val bestMatchesList=api.getSearches(keywords=searchQuery).bestMatches
            if(bestMatchesList.isNotEmpty()) Resource.Loading(data=false)
            Resource.Success(data=bestMatchesList)
        }catch(exception: Exception){
            Resource.Error(message = exception.message.toString())
        }
    }





//    suspend fun getTopGainerStocks( searchQuery: String) : Resource<List<TopGainer>>{
//        return try{
//            Resource.Loading(data=true)
//            val topGainerList=getStocks(searchQuery).data?.top_gainers
//            if(topGainerList!!.isNotEmpty()) Resource.Loading(data=false)
//            Resource.Success(data=topGainerList)
//        }catch(exception: Exception){
//            Resource.Error(message = exception.message.toString())
//        }
//    }
//
//    suspend fun getTopLoserStocks( searchQuery: String) : Resource<List<TopLoser>>{
//        return try{
//            Resource.Loading(data=true)
//            val topLoserList=getStocks(searchQuery).data?.top_losers
//            if(topLoserList!!.isNotEmpty()) Resource.Loading(data=false)
//            Resource.Success(data=topLoserList)
//        }catch(exception: Exception){
//            Resource.Error(message = exception.message.toString())
//        }
//    }




}
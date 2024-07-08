package com.example.stocksapp.screens.ExploreScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocksapp.data.Resource
import com.example.stocksapp.model.TopGainersandLosers.TopGainer
import com.example.stocksapp.model.TopGainersandLosers.TopLoser
import com.example.stocksapp.repository.StocksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreScreenViewModel @Inject constructor(private val repository: StocksRepository)
    : ViewModel(){
    var list1: List<TopGainer> by mutableStateOf(listOf())
    var list2: List<TopLoser> by mutableStateOf(listOf())
    var isloading1:Boolean by mutableStateOf(true)
    var isloading2:Boolean by mutableStateOf(true)


    init {
        loadStocks()
    }

    private fun loadStocks() {
        loadGainerStocks("TOP_GAINERS_LOSERS")
        loadLoserStocks("TOP_GAINERS_LOSERS")
    }

    fun loadGainerStocks(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()){
                return@launch
            }

            try{
                when(val response=repository.getTopGainerStocks(query)){
                    is Resource.Success->{
                        list1=response.data!!
                        if(list1.isNotEmpty()) isloading1=false
                    }
                    is Resource.Error->{
                        Log.e("Network", "searxhBooks: Failed getting gainer stocks")
                    }else->{isloading1=false}
                }
            }catch (exception: Exception){
                isloading1=false
                Log.d("Network", "loadGainerStocks: ${exception.message.toString()}")
            }
        }
    }
    fun loadLoserStocks(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()){
                return@launch
            }

            try{
                when(val response=repository.getTopLoserStocks(query)){
                    is Resource.Success->{
                        list2=response.data!!
                        if(list2.isNotEmpty()) isloading2=false
                    }
                    is Resource.Error->{
                        Log.e("Network", "searchBooks: Failed getting loser stocks")
                    }else->{isloading2=false}
                }
            }catch (exception: Exception){
                isloading2=false
                Log.d("Network", "loadLoserStocks: ${exception.message.toString()}")
            }
        }
    }
}
package com.example.stocksapp.screens.ProductScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocksapp.data.Resource
import com.example.stocksapp.model.CompanyOverview.CompanyOverview
import com.example.stocksapp.model.TopGainersandLosers.TopGainer
import com.example.stocksapp.model.TopGainersandLosers.TopLoser
import com.example.stocksapp.repository.StocksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductScreenViewModel @Inject constructor(private val repository: StocksRepository)
    : ViewModel(){

    var companyOverview by mutableStateOf<CompanyOverview?>(null)
    var isloading:Boolean by mutableStateOf(true)

    fun loadCompanyOverview(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()){
                return@launch
            }

            try{
                when(val response=repository.getCompanyOverviewStock(query)){
                    is Resource.Success->{
                        companyOverview=response.data
                        if(companyOverview?.Symbol!!.isNotBlank()) isloading=false
                    }
                    is Resource.Error->{
                        Log.e("Network", "Failed getting company overview : {${response.message}}")
                    }else->{isloading=false}
                }
            }catch (exception: Exception){
                isloading=false
                Log.d("Network", "Company Overview: ${exception.message.toString()}")
            }
        }
    }
}
package com.example.stocksapp.screens.ProductScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.isNotNull
import com.example.stocksapp.data.Resource
import com.example.stocksapp.model.CompanyOverview.CompanyOverview
import com.example.stocksapp.model.CompanyOverview.TimeSeriesMonthy.X19991231
import com.example.stocksapp.model.StockProfileImage.StockProfileImage
import com.example.stocksapp.repository.StocksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
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

    var timeSeriesOverview by mutableStateOf<Map<String, X19991231>?>(null)
    var isloading2:Boolean by mutableStateOf(true)

    fun loadMonthlyTimeSeries(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()){
                return@launch
            }

            try{
                when(val response=repository.getMonthlyTimeSeries(query)){
                    is Resource.Success->{
                        timeSeriesOverview=response.data!!
                        if(timeSeriesOverview.isNotNull()) isloading2=false
                    }
                    is Resource.Error->{
                        Log.e("Network", "Failed getting time series overview : {${response.message}}")
                    }else->{isloading2=false}
                }
            }catch (exception: Exception){
                isloading2=false
                Log.d("Network", "Time Series Overview: ${exception.message.toString()}")
            }
        }
    }


    var companyOverviewImage by mutableStateOf<StockProfileImage?>(null)
    var isloading1:Boolean by mutableStateOf(true)

    fun loadCompanyOverviewImage(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()){
                return@launch
            }

            try{
                when(val response=repository.getCompanyOverviewStockImage(query)){
                    is Resource.Success->{
                        companyOverviewImage=response.data
                        if(companyOverviewImage?.name!!.isNotBlank()) isloading1=false
                    }
                    is Resource.Error->{
                        Log.e("Network", "Failed getting company overview image : {${response.message}}")
                    }else->{isloading1=false}
                }
            }catch (exception: Exception){
                isloading1=false
                Log.d("Network", "Company Overview image: ${exception.message.toString()}")
            }
        }
    }
}
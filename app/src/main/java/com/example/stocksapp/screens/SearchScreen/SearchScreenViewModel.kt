package com.example.stocksapp.screens.SearchScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocksapp.data.Resource
import com.example.stocksapp.model.TickerSearch.BestMatche
import com.example.stocksapp.repository.StocksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


data class SearchUiState(
    val stockPrefix: String = "",
    val stockList: List<BestMatche> = emptyList(),
    val selectedStock: BestMatche? = null
)

@HiltViewModel
class SearchScreenViewModel @Inject constructor(private val repository: StocksRepository)
    : ViewModel() {
    val _searchText = MutableStateFlow("")
    val _stockList = MutableStateFlow<List<BestMatche>>(emptyList())
    val _selectedStock :MutableStateFlow<BestMatche?> = MutableStateFlow(null)
    var isloading:Boolean by mutableStateOf(true)

    val searchUiState = combine(_searchText, _stockList, _selectedStock) {
            searchText, stockList, selectedStock ->

        if (searchText.isBlank())
            clearSearch()

        SearchUiState(
            searchText,
            stockList, selectedStock
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SearchUiState()
    )

    init {
        _searchText
            .debounce(300) // gets the latest; no need for delays!
            .filter { stockPrefix -> (stockPrefix.length > 1) } // make sure there's enough initial text to search for
            .distinctUntilChanged() // to avoid duplicate network calls
            .flowOn(Dispatchers.IO) // Changes the context where this flow is executed to Dispatchers.IO
            .onEach { stockPrefix -> // just gets the prefix: 'ph', 'pho', 'phoe'
                loadSearches(stockPrefix)
            }
            .launchIn(viewModelScope)
    }


    private fun clearSearch() {
        _stockList.value = emptyList()
        _selectedStock.value = null
    }


    fun loadSearches(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()){
                return@launch
            }

            try{
                when(val response=repository.getBestMatches(query)){
                    is Resource.Success->{
                        _stockList.value=response.data!!
                        if(_stockList.value.isNotEmpty()) isloading=false
                    }
                    is Resource.Error->{
                        Log.e("Network", "Failed getting company searches: {${response.message}}")
                    }else->{isloading=false}
                }
            }catch (exception: Exception){
                isloading=false
                Log.d("Network", "searches: ${exception.message.toString()}")
            }
        }
    }


}
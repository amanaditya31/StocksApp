package com.example.stocksapp.model.TopGainersandLosers

data class TopGainersandLosers(
    val last_updated: String,
    val metadata: String,
    val most_actively_traded: List<MostActivelyTraded>,
    val top_gainers: List<TopGainer>,
    val top_losers: List<TopLoser>
) {
    fun getTopGainers(): List<TopGainer> = top_gainers
    fun getTopLosers(): List<TopLoser> = top_losers
    fun getMostActivelyTraded(): List<MostActivelyTraded> = most_actively_traded
}
package com.example.stocksapp.model.TopGainersandLosers

data class TopGainer(
    val change_amount: String,
    val change_percentage: String,
    val price: String,
    val ticker: String,
    val volume: String
)
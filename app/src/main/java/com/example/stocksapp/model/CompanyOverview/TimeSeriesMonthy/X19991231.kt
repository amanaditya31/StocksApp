package com.example.stocksapp.model.CompanyOverview.TimeSeriesMonthy

import com.squareup.moshi.Json

data class X19991231(
    @Json(name = "1. open") val open: String,
    @Json(name = "2. high") val high: String,
    @Json(name = "3. low") val low: String,
    @Json(name = "4. close") val close: String,
    @Json(name = "5. volume") val volume: String
)
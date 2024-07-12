package com.example.stocksapp.model.CompanyOverview.TimeSeriesMonthy

import com.squareup.moshi.Json

data class MetaData(
    @Json(name = "1. Information") val information: String,
    @Json(name = "2. Symbol") val symbol: String,
    @Json(name = "3. Last Refreshed") val lastRefreshed: String,
    @Json(name = "4. Time Zone") val timeZone: String
)
package com.example.stocksapp.model.CompanyOverview.TimeSeriesMonthy

import com.squareup.moshi.Json

data class TimeSeriesMonthly(
//    val Meta Data: MetaData,
//    val Monthly Time Series: MonthlyTimeSeries
    @Json(name = "Meta Data") val metaData: MetaData,
    @Json(name = "Monthly Time Series") val monthlyTimeSeries: Map<String, X19991231>
)
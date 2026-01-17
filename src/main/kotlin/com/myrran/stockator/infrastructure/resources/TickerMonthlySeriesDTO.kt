package com.myrran.stockator.infrastructure.resources

import java.time.LocalDate
import java.time.Month

data class TickerMonthlySeriesDTO(
    val tickerId: String,
    val firstDate: LocalDate,
    val lastDate: LocalDate,
    val yearIncrease: Map<Int, Double>,
    val averageIncreasesByMonth: Map<Month, Double>,
    val medianIncreasesByMonth: Map<Month, Double>,
    val numberOfNegativeIncreasesByMonth: Map<Month, Int>,
    val monthlyData: Map<Int, Map<String, MonthlyRatesDTO>>,
)

data class MonthlyRatesDTO(
    val openingPrice: Double,
    val closingPrice: Double,
    val closingDay: LocalDate,
    val increase: Double?
)

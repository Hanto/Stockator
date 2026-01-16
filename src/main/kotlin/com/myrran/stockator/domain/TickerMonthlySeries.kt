package com.myrran.stockator.domain

import java.time.LocalDate

data class TickerMonthlySeries(
    val ticker: Ticker,
    val months: Map<LocalDate, MonthlyData>
)

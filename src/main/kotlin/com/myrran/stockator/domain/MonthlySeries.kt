package com.myrran.stockator.domain

import java.time.LocalDate

data class MonthlySeries(
    val ticker: Ticker,
    val months: Map<LocalDate, MonthlyData>
)

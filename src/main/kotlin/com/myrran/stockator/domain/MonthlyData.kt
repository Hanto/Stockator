package com.myrran.stockator.domain

import java.time.LocalDate

class MonthlyData(
    val openingPrice: Money,
    val closingPrice: Money,
    val closingDay: LocalDate
) {
    val increase: Percentage = closingPrice / openingPrice
}

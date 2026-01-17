package com.myrran.stockator.domain.tickerhistory

import com.myrran.stockator.domain.misc.Increase
import java.time.LocalDate
import java.time.Month

data class TickerHistory(
    val tickerId: TickerId,
    val monthlyHistory: MonthlyHistory
){
    fun firstDate(): LocalDate =
        monthlyHistory.firstDate()

    fun lastDate(): LocalDate =
        monthlyHistory.lastDate()

    fun averageIncreaseOf(month: Month): Increase =
        monthlyHistory.averageIncreaseOf(month)

    fun medianIncreaseOf(month: Month): Increase =
        monthlyHistory.medianIncreaseOf(month)

    fun numberOfNegativeIncreasesOn(month: Month): Int =
        monthlyHistory.numberOfNegativeIncreasesOn(month)
}

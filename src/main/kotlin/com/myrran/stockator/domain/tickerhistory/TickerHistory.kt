package com.myrran.stockator.domain.tickerhistory

import com.myrran.stockator.domain.misc.Increase
import com.myrran.stockator.domain.misc.average
import com.myrran.stockator.domain.misc.median
import java.time.LocalDate
import java.time.Month

data class TickerHistory(
    val ticker: Ticker,
    val monthlyHistory: MonthlyHistory
){
    fun firstDate(): LocalDate =
        monthlyHistory.firstDate()

    fun lastDate(): LocalDate =
        monthlyHistory.lastDate()

    fun averageIncreaseOf(month: Month): Increase =
        monthlyHistory.byMonth[month]?.map { it.increase }?.average() ?: Increase(0.0)

    fun medianIncreaseOf(month: Month): Increase =
        monthlyHistory.byMonth[month]?.map { it.increase }?.median() ?: Increase(0.0)

    fun numberOfNegativeIncreasesOn(month: Month): Int =
        monthlyHistory.byMonth[month]?.map { it.increase}?.filterIsInstance<Increase>()?.count { it.value < 0 } ?: 0
}

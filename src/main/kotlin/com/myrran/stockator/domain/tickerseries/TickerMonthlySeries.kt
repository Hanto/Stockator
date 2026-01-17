package com.myrran.stockator.domain.tickerseries

import com.myrran.stockator.domain.misc.Increase
import com.myrran.stockator.domain.misc.Year
import com.myrran.stockator.domain.misc.average
import com.myrran.stockator.domain.misc.median
import java.time.LocalDate
import java.time.Month

data class TickerMonthlySeries(
    val ticker: Ticker,
    val monthlyData: List<MonthlyData>
){
    val byYearAndMonth: Map<Year, Map<Month, MonthlyData>> = monthlyData
        .groupBy { Year(it.closingDay.year) }
        .mapValues { entry -> entry.value.associateBy { it.closingDay.month } }

    val byMonth: Map<Month, List<MonthlyData>> = monthlyData
        .groupBy { it.closingDay.month }

    fun firstDate(): LocalDate =
        monthlyData.minOfOrNull { it.closingDay }!!

    fun lastDate(): LocalDate =
        monthlyData.maxOfOrNull { it.closingDay }!!

    fun averageIncreaseOf(month: Month): Increase =
        byMonth[month]?.map { it.increase }?.average() ?: Increase(0.0)

    fun medianIncreaseOf(month: Month): Increase =
        byMonth[month]?.map { it.increase }?.median() ?: Increase(0.0)

    fun numberOfNegativeIncreasesOn(month: Month): Int =
        byMonth[month]?.map { it.increase}?.filterIsInstance<Increase>()?.count { it.value < 0 } ?: 0
}

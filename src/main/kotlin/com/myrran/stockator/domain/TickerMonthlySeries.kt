package com.myrran.stockator.domain

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

    fun averageIncreaseOf(month: Month): Percentage =
        byMonth[month]?.map { it.increase }?.average() ?: Percentage(0.0)

    fun medianIncreaseOf(month: Month): Percentage =
        byMonth[month]?.map { it.increase }?.median() ?: Percentage(0.0)

    fun numberOfNegativeIncreasesOn(month: Month): Int =
        byMonth[month]?.map { it.increase}?.filterIsInstance<Percentage>()?.count { it.value > 0 } ?: 0
}

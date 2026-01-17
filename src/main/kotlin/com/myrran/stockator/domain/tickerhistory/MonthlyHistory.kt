package com.myrran.stockator.domain.tickerhistory

import com.myrran.stockator.domain.misc.Year
import java.time.LocalDate
import java.time.Month

class MonthlyHistory(
    val monthlyRates: List<MonthlyRates>
) {
    val byYearAndMonth: Map<Year, Map<Month, MonthlyRates>> = monthlyRates
        .groupBy { Year(it.closingDay.year) }
        .mapValues { entry -> entry.value.associateBy { it.closingDay.month } }

    val byMonth: Map<Month, List<MonthlyRates>> = monthlyRates
        .groupBy { it.closingDay.month }

    fun firstDate(): LocalDate =
        monthlyRates.minOfOrNull { it.closingDay }!!

    fun lastDate(): LocalDate =
        monthlyRates.maxOfOrNull { it.closingDay }!!
}

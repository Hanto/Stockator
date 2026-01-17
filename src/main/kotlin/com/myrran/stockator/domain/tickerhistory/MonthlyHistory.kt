package com.myrran.stockator.domain.tickerhistory

import com.myrran.stockator.domain.misc.Increase
import com.myrran.stockator.domain.misc.Year
import com.myrran.stockator.domain.misc.average
import com.myrran.stockator.domain.misc.median
import java.time.LocalDate
import java.time.Month

class MonthlyHistory(
    val monthlyRates: List<MonthlyRates>
) {
    private val byYearAndMonth: Map<Year, Map<Month, MonthlyRates>> = monthlyRates
        .groupBy { Year(it.closingDay.year) }
        .mapValues { entry -> entry.value.associateBy { it.closingDay.month } }

    private val byMonth: Map<Month, List<MonthlyRates>> = monthlyRates
        .groupBy { it.closingDay.month }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun firstDate(): LocalDate =
        monthlyRates.minOfOrNull { it.closingDay }!!

    fun lastDate(): LocalDate =
        monthlyRates.maxOfOrNull { it.closingDay }!!

    fun averageIncreaseOf(month: Month): Increase =
        byMonth[month]
            ?.map { it.increase }
            ?.average() ?: Increase(0.0)

    fun medianIncreaseOf(month: Month): Increase =
        byMonth[month]
            ?.map { it.increase }
            ?.median() ?: Increase(0.0)

    fun numberOfNegativeIncreasesOn(month: Month): Int =
        byMonth[month]
            ?.map { it.increase}
            ?.filterIsInstance<Increase>()
            ?.count { it.value < 0 } ?: 0
}

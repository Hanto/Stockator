package com.myrran.stockator.domain.tickerhistory

import com.myrran.stockator.domain.misc.Increase
import com.myrran.stockator.domain.misc.Year
import com.myrran.stockator.domain.misc.average
import com.myrran.stockator.domain.misc.median
import java.time.LocalDate
import java.time.Month

class History(
    val monthlyHistory: List<MonthHistory>
) {
    private val byYearAndMonth: Map<Year, YearHistory> = monthlyHistory
        .groupBy { Year(it.closingDay.year) }
        .mapValues { entry -> YearHistory(entry.value) }

    private val byMonth: Map<Month, List<MonthHistory>> = monthlyHistory
        .groupBy { it.closingDay.month }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun firstDate(): LocalDate =
        monthlyHistory.minOfOrNull { it.closingDay }!!

    fun lastDate(): LocalDate =
        monthlyHistory.maxOfOrNull { it.closingDay }!!

    fun yearlyIncrease(): Map<Year, Increase> =
        byYearAndMonth
            .mapValues { it.value.increase() }

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
            ?.count { it.value < 0 } ?: 0
}

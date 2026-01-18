package com.myrran.stockator.domain.tickerhistory

import com.myrran.stockator.domain.misc.Increase
import com.myrran.stockator.domain.misc.Money
import java.time.LocalDate
import java.time.Month

class YearHistory(
    private val monthlyHistory: List<MonthHistory>
)
{
    private val byMonth: Map<Month, MonthHistory> =
        monthlyHistory.associateBy { it.closingDay.month }

    fun firstDate(): LocalDate =
        monthlyHistory.minOfOrNull { it.closingDay }!!

    fun lastDate(): LocalDate =
        monthlyHistory.maxOfOrNull { it.closingDay }!!

    fun openingPrice(): Money =
        byMonth[firstDate().month]!!.openingPrice

    fun closingPrice(): Money =
        byMonth[lastDate().month]!!.closingPrice

    fun increase(): Increase = (closingPrice() / openingPrice()).toIncrease()
}

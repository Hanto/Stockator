package com.myrran.stockator.domain.rules

import com.myrran.stockator.domain.misc.Increase
import com.myrran.stockator.domain.misc.Percentage
import com.myrran.stockator.domain.tickerhistory.TickerHistory
import java.time.Month

sealed interface TickerHistoryRules {

    fun satisfiesTheRules(series: TickerHistory, month: Month): Boolean
}

data class RulesForAGoodMonth(

    val minimumYearsOfHistory: Int,
    val minimumAverageIncrease: Increase,
    val minimumMedianComparedToAverage: Percentage,
    val maximumNumberOfNegativeIncreases: Int,

): TickerHistoryRules {

    override fun satisfiesTheRules(series: TickerHistory, month: Month): Boolean {

        val yearsOfHistory = series.firstDate().until(series.lastDate()).years
        val average = series.averageIncreaseOf(month)
        val median = series.medianIncreaseOf(month)
        val badMonths = series.numberOfNegativeIncreasesOn(month)

        val hasEnoughHistory = yearsOfHistory >= minimumYearsOfHistory
        val averageIsOk = average > minimumAverageIncrease
        val medianIsOk = median >= (average * minimumMedianComparedToAverage)
        val badMonthsAreOk = badMonths <= maximumNumberOfNegativeIncreases

        return hasEnoughHistory && averageIsOk && medianIsOk && badMonthsAreOk
    }
}

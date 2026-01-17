package com.myrran.stockator.domain.rules

import com.myrran.stockator.domain.misc.Increase
import com.myrran.stockator.domain.misc.Percentage
import com.myrran.stockator.domain.tickerseries.TickerMonthlySeries
import java.time.Month

sealed interface RulesForAGoodMonthI {

    fun satisfiesTheRules(series: TickerMonthlySeries, month: Month): Boolean
}

data class RulesForAGoodMonth(

    val minimumAverageIncrease: Increase,
    val minimumMedianComparedToAverage: Percentage,
    val maximumNumberOfNegativeIncreases: Int

): RulesForAGoodMonthI {

    override fun satisfiesTheRules(series: TickerMonthlySeries, month: Month): Boolean {

        val average = series.averageIncreaseOf(month)
        val median = series.medianIncreaseOf(month)
        val badMonths = series.numberOfNegativeIncreasesOn(month)

        val averageIsOk = average > minimumAverageIncrease
        val medianIsOk = median >= (average * minimumMedianComparedToAverage)
        val badMonthsAreOk = badMonths <= maximumNumberOfNegativeIncreases

        return  averageIsOk && medianIsOk && badMonthsAreOk
    }
}

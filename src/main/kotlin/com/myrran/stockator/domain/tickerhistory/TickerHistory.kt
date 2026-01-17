package com.myrran.stockator.domain.tickerhistory

import com.myrran.stockator.domain.misc.Increase
import com.myrran.stockator.domain.rules.TickerHistoryRules
import java.time.LocalDate
import java.time.Month

data class TickerHistory(
    val tickerId: TickerId,
    val history: History
){
    fun firstDate(): LocalDate =
        history.firstDate()

    fun lastDate(): LocalDate =
        history.lastDate()

    fun averageIncreaseOf(month: Month): Increase =
        history.averageIncreaseOf(month)

    fun medianIncreaseOf(month: Month): Increase =
        history.medianIncreaseOf(month)

    fun numberOfNegativeIncreasesOn(month: Month): Int =
        history.numberOfNegativeIncreasesOn(month)

    fun satisfiesRulesThatMonth(rules: TickerHistoryRules, month: Month): Boolean =
        rules.satisfiesTheRules(this, month)

    fun satisfiesRulesAnyMonth(rules: TickerHistoryRules): Boolean =
        Month.entries.any { rules.satisfiesTheRules(this, it) }
}

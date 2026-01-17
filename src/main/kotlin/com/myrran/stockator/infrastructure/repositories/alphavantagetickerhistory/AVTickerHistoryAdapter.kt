package com.myrran.stockator.infrastructure.repositories.alphavantagetickerhistory

import com.myrran.stockator.domain.misc.Money
import com.myrran.stockator.domain.misc.TimeRange
import com.myrran.stockator.domain.tickerhistory.MonthlyHistory
import com.myrran.stockator.domain.tickerhistory.MonthlyRates
import com.myrran.stockator.domain.tickerhistory.TickerHistory
import com.myrran.stockator.domain.tickerhistory.TickerId
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Component
class AVTickerHistoryAdapter {

    fun toDomain(entity: AVTickerHistoryEntity, timeRange: TimeRange): TickerHistory {

        val monthlyRatesRaw = entity.monthlyHistory.entries
            .map { toMonthlyDataRaw(toLocalDate(it.key), it.value) }
            .filter { it.closingDay.hasLessThan(timeRange.amount, timeRange.unit) }

        val mapByYearAndMonth = monthlyRatesRaw
            .groupBy { it.closingDay.year }
            .mapValues { entry -> entry.value.associateBy { it.closingDay.monthValue } }

        val monthlyRates = monthlyRatesRaw
            .map { toMonthlyRates(it, mapByYearAndMonth) }

        return TickerHistory(
            tickerId = TickerId(entity.metadata.symbol),
            monthlyHistory = MonthlyHistory(monthlyRates)
        )
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun toMonthlyDataRaw(closingDate: LocalDate, entity: AVMonthlyRatesEntity): MonthlyRatesRaw =

        MonthlyRatesRaw(
            closingDay = closingDate,
            closingPrice = Money(entity.close.toDouble())
        )

    private fun toMonthlyRates(entity: MonthlyRatesRaw, mapByYearAndMonth: Map<Int, Map<Int, MonthlyRatesRaw>>): MonthlyRates =

        MonthlyRates(
            openingPrice = mapByYearAndMonth.getPreviousMonthClosingPrice(entity.closingDay.previousMonth()),
            closingDay = entity.closingDay,
            closingPrice = entity.closingPrice
        )

    private fun toLocalDate(dateString: String): LocalDate =

        LocalDate.parse(dateString)

    private fun LocalDate.previousMonth(): LocalDate =

        this.minusMonths(1)

    private fun Map<Int, Map<Int, MonthlyRatesRaw>>.getPreviousMonthClosingPrice(date: LocalDate): Money =

        this[date.year]?.get(date.monthValue)?.closingPrice ?: Money(0.0)

    fun LocalDate.hasLessThan(amount: Long, unit: ChronoUnit): Boolean =
        LocalDate.now().minus(amount, unit) < this

    private data class MonthlyRatesRaw(
        val closingDay: LocalDate,
        val closingPrice: Money
    )
}

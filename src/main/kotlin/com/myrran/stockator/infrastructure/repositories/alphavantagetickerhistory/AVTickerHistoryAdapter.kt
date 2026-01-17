package com.myrran.stockator.infrastructure.repositories.alphavantagetickerhistory

import com.myrran.stockator.domain.misc.Money
import com.myrran.stockator.domain.tickerhistory.MonthlyHistory
import com.myrran.stockator.domain.tickerhistory.MonthlyRates
import com.myrran.stockator.domain.tickerhistory.Ticker
import com.myrran.stockator.domain.tickerhistory.TickerHistory
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class AVTickerHistoryAdapter {

    fun toDomain(entity: AVTickerHistoryEntity): TickerHistory {

        val mapByYearAndMonth: Map<Int, Map<Int, MonthlyDataRaw>> = toMapByYearAndMonth(entity.monthlyHistory)
        val monthlyRates = mapByYearAndMonth
            .flatMap { it.value.values }
            .map { toMonthlyData(it, mapByYearAndMonth) }

        return TickerHistory(
            ticker = Ticker(entity.metadata.symbol),
            monthlyHistory = MonthlyHistory(monthlyRates)
        )
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun toMapByYearAndMonth(entities: Map<String, AVMonthlyRatesEntity>): Map<Int, Map<Int, MonthlyDataRaw>> =

        entities
            .mapKeys { toLocalDate(it.key) }
            .entries
            .groupBy { it.key.year }
            .mapValues { entry -> entry.value.associate { it.key.monthValue to toMonthlyDataRaw(it.value, it.key) } }

    private fun toMonthlyData(entity: MonthlyDataRaw, mapByYearAndMonth: Map<Int, Map<Int, MonthlyDataRaw>>): MonthlyRates =

        MonthlyRates(
            openingPrice = mapByYearAndMonth.getPreviousMonthClosingPrice(entity.closingDay.previousMonth()),
            closingDay = entity.closingDay,
            closingPrice = entity.closingPrice
        )

    private fun toLocalDate(dateString: String): LocalDate =

        LocalDate.parse(dateString)

    private fun toMonthlyDataRaw(entity: AVMonthlyRatesEntity, closingDate: LocalDate): MonthlyDataRaw =

        MonthlyDataRaw(
            closingPrice = Money(entity.close.toDouble()),
            closingDay = closingDate
        )

    private fun LocalDate.previousMonth(): LocalDate =

        this.minusMonths(1)

    private fun Map<Int, Map<Int, MonthlyDataRaw>>.getPreviousMonthClosingPrice(date: LocalDate): Money =

        this[date.year]?.get(date.monthValue)?.closingPrice ?: Money(0.0)

    private data class MonthlyDataRaw(
        val closingDay: LocalDate,
        val closingPrice: Money
    )
}

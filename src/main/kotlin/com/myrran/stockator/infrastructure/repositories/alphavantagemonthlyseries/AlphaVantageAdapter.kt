package com.myrran.stockator.infrastructure.repositories.alphavantagemonthlyseries

import com.myrran.stockator.domain.Money
import com.myrran.stockator.domain.MonthlyData
import com.myrran.stockator.domain.Ticker
import com.myrran.stockator.domain.TickerMonthlySeries
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class AlphaVantageAdapter {

    fun toDomain(entity: AVTickerMonthlySeriesEntity): TickerMonthlySeries {

        val mapByYearAndMonth: Map<Int, Map<Int, MonthlyDataRaw>> = toMapByYearAndMonth(entity.monthlyTimeSeries)
        val monthlyData = mapByYearAndMonth
            .flatMap { it.value.values }
            .map { toMonthlyData(it, mapByYearAndMonth) }

        return TickerMonthlySeries(
            ticker = Ticker(entity.metadata.symbol),
            monthlyData = monthlyData
        )
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun toMapByYearAndMonth(entities: Map<String, AVMonthlyDataEntity>): Map<Int, Map<Int, MonthlyDataRaw>> =

        entities
            .mapKeys { toLocalDate(it.key) }
            .entries
            .groupBy { it.key.year }
            .mapValues { entry -> entry.value.associate { it.key.monthValue to toMonthlyDataRaw(it.value, it.key) } }

    private fun toMonthlyData(entity: MonthlyDataRaw, mapByYearAndMonth: Map<Int, Map<Int, MonthlyDataRaw>>): MonthlyData =

        MonthlyData(
            openingPrice = mapByYearAndMonth.getPreviousMonthClosingPrice(entity.closingDay.previousMonth()),
            closingDay = entity.closingDay,
            closingPrice = entity.closingPrice
        )

    private fun toLocalDate(dateString: String): LocalDate =

        LocalDate.parse(dateString)

    private fun toMonthlyDataRaw(entity: AVMonthlyDataEntity, closingDate: LocalDate): MonthlyDataRaw =

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

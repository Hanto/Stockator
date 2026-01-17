package com.myrran.stockator.infrastructure.resources

import com.myrran.stockator.domain.misc.Increase
import com.myrran.stockator.domain.misc.Percentage
import com.myrran.stockator.domain.misc.TimeRange
import com.myrran.stockator.domain.rules.RulesForAGoodMonth
import com.myrran.stockator.domain.tickerhistory.TickerId
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class TickerAnalyzerProperties(

    @Value($$"#{'${default.tickers}'.split(',')}")
    val defaultTickers: List<String>,

    @Value($$"${default.tickers.historicalTimeRange.amount}")
    val defaultHistoricalTimeRangeAmount: Long,

    @Value($$"${default.tickers.historicalTimeRange.unit}")
    val defaultHistoricalTimeRangeUnit: String,

    @Value($$"${default.rules.goodMonth.minimumAverageIncrease}")
    val defaultGoodMonthMinimumAverageIncrease: Double,

    @Value($$"${default.rules.goodMonth.mimumMedianComparedToAverage}")
    val defaultGoodMonthMimumMedianComparedToAverage: Double,

    @Value($$"${default.rules.goodMonth.maximumNumberOfNegativeIncreases}")
    val defaultGoodMonthMaximumNumberOfNegativeIncreases: Int
) {
    fun defaultTimeRange(): TimeRange =
        TimeRange(
            amount = defaultHistoricalTimeRangeAmount,
            unit = java.time.temporal.ChronoUnit.valueOf(defaultHistoricalTimeRangeUnit)
        )

    fun defaultTickers(): List<TickerId> =
        defaultTickers.map { TickerId(it) }

    fun defaultRulesForAGoodMonth() =
        RulesForAGoodMonth(
            minimumAverageIncrease = Increase(defaultGoodMonthMinimumAverageIncrease),
            minimumMedianComparedToAverage = Percentage(defaultGoodMonthMimumMedianComparedToAverage),
            maximumNumberOfNegativeIncreases = defaultGoodMonthMaximumNumberOfNegativeIncreases
        )
}

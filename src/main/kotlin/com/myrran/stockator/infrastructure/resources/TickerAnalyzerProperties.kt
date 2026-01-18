package com.myrran.stockator.infrastructure.resources

import com.myrran.stockator.domain.misc.Increase
import com.myrran.stockator.domain.misc.Percentage
import com.myrran.stockator.domain.misc.TimeRange
import com.myrran.stockator.domain.rules.RulesForAGoodMonth
import com.myrran.stockator.domain.tickerhistory.TickerId
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import java.nio.file.Files
import java.nio.file.Path
import java.time.temporal.ChronoUnit

@Configuration
class TickerAnalyzerProperties(

    @Value($$"${default.tickers.listsource}")
    val defaultTickersList: Resource,

    @Value($$"${default.tickers.historicalTimeRange.amount}")
    val defaultHistoricalTimeRangeAmount: Long,

    @Value($$"${default.tickers.historicalTimeRange.unit}")
    val defaultHistoricalTimeRangeUnit: String,

    @Value($$"${default.rules.goodMonth.minimumYearsOfHistory}")
    val defaultMinimumYearsOfHistory: Int,

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
            unit = ChronoUnit.valueOf(defaultHistoricalTimeRangeUnit)
        )

    fun defaultRulesForAGoodMonth() =
        RulesForAGoodMonth(
            minimumYearsOfHistory = defaultMinimumYearsOfHistory,
            minimumAverageIncrease = Increase(defaultGoodMonthMinimumAverageIncrease),
            minimumMedianComparedToAverage = Percentage(defaultGoodMonthMimumMedianComparedToAverage),
            maximumNumberOfNegativeIncreases = defaultGoodMonthMaximumNumberOfNegativeIncreases
        )

    fun defaultTickers(): List<TickerId> =
         Files.readString(Path.of(defaultTickersList.uri))
             .split(",")
             .map { TickerId(it) }
}

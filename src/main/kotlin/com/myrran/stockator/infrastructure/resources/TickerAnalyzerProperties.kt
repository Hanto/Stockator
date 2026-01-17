package com.myrran.stockator.infrastructure.resources

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class TickerAnalyzerProperties(

    @Value($$"#{'${default.tickers}'.split(',')}")
    val defaultTickers: List<String>,

    @Value($$"${default.rules.goodMonth.minimumAverageIncrease}")
    val goodMonthMinimumAverageIncrease: Double,

    @Value($$"${default.rules.goodMonth.mimumMedianComparedToAverage}")
    val goodMonthMimumMedianComparedToAverage: Double,

    @Value($$"${default.rules.goodMonth.maximumNumberOfNegativeIncreases}")
    val goodMonthMaximumNumberOfNegativeIncreases: Int
)

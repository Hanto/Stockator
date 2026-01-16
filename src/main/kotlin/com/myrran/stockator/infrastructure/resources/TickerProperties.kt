package com.myrran.stockator.infrastructure.resources

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class TickerProperties(

    @Value($$"#{'${default.tickers}'.split(',')}")
    val defaultTickers: List<String>,
)

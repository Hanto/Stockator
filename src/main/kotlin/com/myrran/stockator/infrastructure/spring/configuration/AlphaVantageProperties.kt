package com.myrran.stockator.infrastructure.spring.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class AlphaVantageProperties(

    @Value($$"${externalapi.alphavantage.apikey}")
    val apiKey: String,
    @Value($$"${externalapi.alphavantage.baseurl}")
    val url: String,
    @Value($$"${externalapi.alphavantage.monthlystockdata}")
    val monthlyFunction: String
)

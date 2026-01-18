package com.myrran.stockator.domain.ticker

import com.myrran.stockator.domain.tickerhistory.TickerId

data class Ticker(

    val tickerId: TickerId,
    val name: TickerName,
)

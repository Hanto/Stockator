package com.myrran.stockator.infrastructure.repositories.alphavantageticker

import com.myrran.stockator.domain.ticker.Ticker
import com.myrran.stockator.domain.ticker.TickerName
import com.myrran.stockator.domain.tickerhistory.TickerId
import org.springframework.stereotype.Component

@Component
class TickerAdapter {

    fun toDomain(entity: TickerStatusEntity): Ticker =
        Ticker(
            tickerId = TickerId(entity.symbol),
            name = TickerName(entity.name),
        )
}

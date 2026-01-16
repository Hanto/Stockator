package com.myrran.stockator.infrastructure.resources

import com.myrran.stockator.domain.Ticker
import com.myrran.stockator.infrastructure.repositories.alphavantagemonthlyseries.AVTickerMonthlySeriesEntity
import com.myrran.stockator.infrastructure.repositories.alphavantagemonthlyseries.AlphaVantageClient
import org.mapdb.DB
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.ConcurrentMap

@RestController
class Temp(
    val client: AlphaVantageClient,
    val mapDB: ConcurrentMap<String, AVTickerMonthlySeriesEntity>,
    val db: DB
) {

    @GetMapping("/api/temp")
    fun pim(): AVTickerMonthlySeriesEntity? {

        return mapDB["GOOG"] ?: pam("GOOG")
    }

    private fun pam(symbol: String): AVTickerMonthlySeriesEntity? {

        val entity = client.findBy(Ticker(symbol))
        mapDB[symbol] = entity
        db.commit()
        return entity
    }
}

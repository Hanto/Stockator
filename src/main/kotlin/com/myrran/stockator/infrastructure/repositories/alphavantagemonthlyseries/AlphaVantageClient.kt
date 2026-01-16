package com.myrran.stockator.infrastructure.repositories.alphavantagemonthlyseries

import com.myrran.stockator.domain.Ticker
import com.myrran.stockator.infrastructure.spring.configuration.AlphaVantageProperties
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestOperations
import org.springframework.web.client.getForObject

@Repository
class AlphaVantageClient(

    val restTemplate: RestOperations,
    val alphaVantageProperties: AlphaVantageProperties
)
{
    private val log = LoggerFactory.getLogger(this.javaClass)

    fun findBy(ticker: Ticker): AVTickerMonthlySeriesEntity? {

        log.info("findBy(ticker={})", ticker)

        val url = """
            ${alphaVantageProperties.url}
            ?function=${alphaVantageProperties.monthlyFunction}
            &symbol=${ticker.symbol}
            &apikey=${alphaVantageProperties.apiKey}
        
        """.trimIndent().replace("\n", "")

        return restTemplate.getForObject<AVTickerMonthlySeriesEntity>(url)
    }
}

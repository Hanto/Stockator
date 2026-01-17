package com.myrran.stockator.infrastructure.repositories.alphavantagemonthlyseries

import com.myrran.stockator.domain.Ticker
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.converter.HttpMessageConversionException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestOperations
import org.springframework.web.client.getForObject

@Repository
class AlphaVantageClient(

    val restTemplate: RestOperations,
    val alphaVantageProperties: AlphaVantageProperties
)
{
    private val log = LoggerFactory.getLogger(this.javaClass)

    @Cacheable(cacheManager = "mapDBCacheManager", cacheNames = ["tickerMonthlySeries"], key = "#ticker.symbol")
    fun findBy(ticker: Ticker): AVTickerMonthlySeriesEntity? =
        try {

            log.info("findBy(ticker={})", ticker)

            val url = """
                ${alphaVantageProperties.url}
                ?function=${alphaVantageProperties.monthlyFunction}
                &apikey=${alphaVantageProperties.apiKey}
                &symbol={symbol}
            
            """.trimIndent().replace("\n", "")

            restTemplate.getForObject<AVTickerMonthlySeriesEntity>(url, ticker.symbol)
        }
        catch (e: RestClientException) {

            when (e.cause) {

                is HttpMessageNotReadableException, is HttpMessageConversionException -> null.also { log.error("No readable for {}", ticker) }
                else -> throw e
            }
        }
}

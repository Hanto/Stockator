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

            val url = """
                ${alphaVantageProperties.url}
                ?function=${alphaVantageProperties.monthlyFunction}
                &symbol={symbol}
                &apikey=${alphaVantageProperties.apiKey}
            
            """.trimIndent().replace("\n", "")

            restTemplate.getForObject<AVTickerMonthlySeriesEntity>(url, ticker.symbol)
                .also { log.info("Fetched monthly series for {}", ticker) }
        }
        catch (e: RestClientException) {

            when (e.cause) {

                is HttpMessageNotReadableException, is HttpMessageConversionException -> null.also { log.warn("No readable for {}", ticker) }
                else -> throw e
            }
        }
}

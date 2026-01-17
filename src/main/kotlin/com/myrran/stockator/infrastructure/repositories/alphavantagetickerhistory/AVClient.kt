package com.myrran.stockator.infrastructure.repositories.alphavantagetickerhistory

import com.myrran.stockator.domain.tickerhistory.TickerId
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.converter.HttpMessageConversionException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestOperations
import org.springframework.web.client.getForObject

@Repository
class AVClient(

    val restTemplate: RestOperations,
    val properties: AVClientProperties
)
{
    private val log = LoggerFactory.getLogger(this.javaClass)

    @Cacheable(cacheManager = "mapDBCacheManager", cacheNames = ["tickerMonthlySeries"], key = "#tickerId.symbol")
    fun findBy(tickerId: TickerId): AVTickerHistoryEntity? =

        try {

            val url = """
                ${properties.url}
                ?function=${properties.monthlyFunction}
                &symbol={symbol}
                &apikey=${properties.apiKey}
            
            """.trimIndent().replace("\n", "")

            restTemplate.getForObject<AVTickerHistoryEntity>(url, tickerId.symbol)
                .also { log.info("Fetched history for: {}", tickerId.symbol) }
        }
        catch (e: RestClientException) {

            when (e.cause) {

                is HttpMessageNotReadableException, is HttpMessageConversionException -> null.also { log.warn("History not readable for {}", tickerId.symbol) }
                else -> throw e
            }
        }

    fun findBy2(tickerId: TickerId): String? =

        try {

            val url = """
                ${properties.url}
                ?function=${properties.monthlyFunction}
                &symbol={symbol}
                &apikey=${properties.apiKey}
            
            """.trimIndent().replace("\n", "")

            restTemplate.getForObject<String>(url, tickerId.symbol)
                .also { log.info("Fetched history for: {}", tickerId.symbol) }
        }
        catch (e: RestClientException) {

            when (e.cause) {

                is HttpMessageNotReadableException, is HttpMessageConversionException -> null.also { log.warn("History not readable for {}", tickerId.symbol) }
                else -> throw e
            }
        }
}

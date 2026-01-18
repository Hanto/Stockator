package com.myrran.stockator.infrastructure.repositories.alphavantagetickerhistory

import com.myrran.stockator.domain.tickerhistory.TickerId
import com.myrran.stockator.infrastructure.repositories.alphavantageticker.TickerStatusEntity
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.converter.HttpMessageConversionException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestOperations
import org.springframework.web.client.getForObject
import java.net.URLEncoder

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

            val encodedSymbol = URLEncoder.encode(tickerId.symbol, "UTF-8")

            restTemplate.getForObject<AVTickerHistoryEntity>(url, encodedSymbol)
                .also { log.info("Fetched history for: {}", tickerId.symbol) }
        }
        catch (e: RestClientException) {

            when (e.cause) {

                is HttpMessageNotReadableException, is HttpMessageConversionException -> null.also { log.warn("History not readable for {}", tickerId.symbol) }
                else -> throw e
            }
        }

    @Cacheable(cacheManager = "mapDBCacheManager", cacheNames = ["tickerStatus"])
    fun findAllTickers(): List<TickerStatusEntity> {

        val url = """
                ${properties.url}
                ?function=LISTING_STATUS
                &apikey=${properties.apiKey}
            
            """.trimIndent().replace("\n", "")

        return restTemplate.getForObject<String>(url)!!
            .also{ log.info("Fetched all tickers listing status") }
            .lines()
            .drop(1)
            .map { it.split(",") }
            .filter { it.size >= 7 }
            .map { TickerStatusEntity(
                symbol = it[0],
                name = it[1],
                exchange = it[2],
                assetType = it[3],
                ipoDate = it[4],
                delistingDate = it[5],
                status = it[6]) }
    }

    // DEBUG STUFF:
    //--------------------------------------------------------------------------------------------------------

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

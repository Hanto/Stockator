package com.myrran.stockator.infrastructure.repositories

import com.myrran.stockator.domain.Ticker
import com.myrran.stockator.infrastructure.repositories.alphavantagemonthlyseries.AlphaVantageClient
import com.myrran.stockator.infrastructure.repositories.alphavantagemonthlyseries.AlphaVantageProperties
import org.junit.jupiter.api.Test
import org.springframework.web.client.RestTemplate

class AlphaVantageClientTest {

    @Test
    fun testFindBy() {

        val client = AlphaVantageClient(
            restTemplate = RestTemplate(),
            alphaVantageProperties = AlphaVantageProperties(
                url = "https://www.alphavantage.co/query",
                apiKey = "demo",
                monthlyFunction = "TIME_SERIES_MONTHLY"
            )
        )

        val ticker = Ticker("IBM")
        val result = client.findBy(ticker)

        println(result)
    }
}

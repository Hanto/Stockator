package com.myrran.stockator.infrastructure.repositories

import com.myrran.stockator.domain.tickerhistory.TickerId
import com.myrran.stockator.infrastructure.repositories.alphavantagetickerhistory.AVClient
import com.myrran.stockator.infrastructure.repositories.alphavantagetickerhistory.AVClientProperties
import org.junit.jupiter.api.Test
import org.springframework.web.client.RestTemplate

class AVClientTest {

    @Test
    fun testFindBy() {

        val client = AVClient(
            restTemplate = RestTemplate(),
            properties = AVClientProperties(
                url = "https://www.alphavantage.co/query",
                apiKey = "demo",
                monthlyFunction = "TIME_SERIES_MONTHLY_ADJUSTED"
            )
        )

        val tickerId = TickerId("IBM")
        val result = client.findBy2(tickerId)

        println(result)
    }
}

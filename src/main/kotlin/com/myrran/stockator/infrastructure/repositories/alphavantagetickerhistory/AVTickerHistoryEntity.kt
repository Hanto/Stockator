package com.myrran.stockator.infrastructure.repositories.alphavantagetickerhistory

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class AVTickerHistoryEntity(

    @JsonProperty("Meta Data")
    val metadata: AVMetadataEntity,
    @JsonProperty("Monthly Adjusted Time Series")
    val monthlyHistory: Map<String, AVMonthlyRatesEntity>

): Serializable

data class AVMetadataEntity(

    @JsonProperty(value = "1. Information")
    val information: String,
    @JsonProperty(value = "2. Symbol")
    val symbol: String,
    @JsonProperty(value = "3. Last Refreshed")
    val lastRefreshed: String,
    @JsonProperty(value = "4. Time Zone")
    val timeZone: String

): Serializable

data class AVMonthlyRatesEntity(

    @JsonProperty(value = "1. open")
    val open: String,
    @JsonProperty(value = "2. high")
    val high: String,
    @JsonProperty(value = "3. low")
    val low: String,
    @JsonProperty(value = "4. close")
    val close: String,
    @JsonProperty(value = "5. adjusted close")
    val adjustedClose: String,
    @JsonProperty(value = "6. volume")
    val volume: String,
    @JsonProperty(value = "7. dividend amount")
    val dividendAmount: String

): Serializable

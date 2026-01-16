package com.myrran.stockator.infrastructure.spring.resttemplatewithretry

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class RetryTemplateProperties(

    @Value($$"${externalapi.retry.default.maxattemps}")
    val maxAttemps: Int,
    @Value($$"#{'${externalapi.retry.default.httpcodes}'.split(',')}")
    val httpCodesToRetry: List<Int>,
    @Value($$"${externalapi.retry.default.backoffinterval}")
    val backOffInterval: Long,
    @Value($$"${externalapi.retry.default.backoffmultiplier}")
    val backOffMultiplier: Double
)

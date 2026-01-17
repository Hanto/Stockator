package com.myrran.stockator.infrastructure.resttemplatewithretry

import org.springframework.boot.restclient.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.retry.support.RetryTemplate

@Configuration
class RestTemplateWithRetryConfiguration {

    @Bean
    fun defaultRetyTemplateForRest(
        retryTemplateProperties: RetryTemplateProperties
    ): RetryTemplate =

        RetryTemplateBuilder(
            maxAttemps = retryTemplateProperties.maxAttemps,
            httpStatusCodeRetry = retryTemplateProperties.httpCodesToRetry,
            backOffInterval = retryTemplateProperties.backOffInterval,
            backOffMultiplier = retryTemplateProperties.backOffMultiplier
        ).build()

    @Bean
    fun alphaVantageRestTemplateWithRetry(
        builder: RestTemplateBuilder,
        defaultRetryTemplateForRest: RetryTemplate
    ): RestTemplateWithRetry =

        RestTemplateWithRetry(
            restTemplate = builder.build(),
            retryTemplate = defaultRetryTemplateForRest
        )
}

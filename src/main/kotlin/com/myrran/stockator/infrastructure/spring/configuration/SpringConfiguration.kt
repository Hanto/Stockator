package com.myrran.stockator.infrastructure.spring.configuration

import com.myrran.stockator.infrastructure.spring.RestTemplateWithRetry
import com.myrran.stockator.infrastructure.spring.RetryTemplateBuilder
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.restclient.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.retry.support.RetryTemplate

@Configuration
@EnableConfigurationProperties
class SpringConfiguration {

    @Bean
    fun defaultRetyTemplateForRest(
        retryProperties: RetryProperties
    ): RetryTemplate =

        RetryTemplateBuilder(
            maxAttemps = retryProperties.maxAttemps,
            httpStatusCodeRetry = retryProperties.httpCodesToRetry,
            backOffInterval = retryProperties.backOffInterval,
            backOffMultiplier = retryProperties.backOffMultiplier
        ).build()


    @Bean
    fun alphaVantageRestTemplate(
        builder: RestTemplateBuilder,
        defaultRetryTemplateForRest: RetryTemplate
    ): RestTemplateWithRetry =

        RestTemplateWithRetry(
            restTemplate = builder.build(),
            retryTemplate = defaultRetryTemplateForRest
        )

}

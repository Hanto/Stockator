package com.myrran.stockator.infrastructure.spring

import com.myrran.stockator.infrastructure.resttemplatewithretry.RestTemplateWithRetry
import com.myrran.stockator.infrastructure.resttemplatewithretry.RetryTemplateBuilder
import com.myrran.stockator.infrastructure.resttemplatewithretry.RetryTemplateProperties
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
        retryTemplateProperties: RetryTemplateProperties
    ): RetryTemplate =

        RetryTemplateBuilder(
            maxAttemps = retryTemplateProperties.maxAttemps,
            httpStatusCodeRetry = retryTemplateProperties.httpCodesToRetry,
            backOffInterval = retryTemplateProperties.backOffInterval,
            backOffMultiplier = retryTemplateProperties.backOffMultiplier
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

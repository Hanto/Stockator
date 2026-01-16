package com.myrran.stockator.infrastructure.resttemplatewithretry

import org.springframework.classify.Classifier
import org.springframework.http.HttpStatusCode
import org.springframework.retry.RetryPolicy
import org.springframework.retry.backoff.ExponentialRandomBackOffPolicy
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy
import org.springframework.retry.policy.NeverRetryPolicy
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate
import org.springframework.web.client.HttpStatusCodeException

class RetryTemplateBuilder(

    private val maxAttemps: Int = 3,
    httpStatusCodeRetry: List<Int>,
    private val backOffInterval: Long,
    private val backOffMultiplier: Double

) {

    private val httpStatusRetry = httpStatusCodeRetry.map { HttpStatusCode.valueOf(it) }.toSet()

    fun build(): RetryTemplate {

        val retryTemplate = RetryTemplate()

        val retryPolicy = ExceptionClassifierRetryPolicy()
        retryPolicy.setExceptionClassifier(configureRetryPolicyBasedOnHttpStatusCode())
        retryTemplate.setRetryPolicy(retryPolicy)

        val backOffPolicy = ExponentialRandomBackOffPolicy();
        backOffPolicy.initialInterval = backOffInterval
        backOffPolicy.maxInterval = 10000
        backOffPolicy.multiplier = backOffMultiplier
        retryTemplate.setBackOffPolicy(backOffPolicy)

        return retryTemplate
    }

    private fun configureRetryPolicyBasedOnHttpStatusCode(): Classifier<Throwable, RetryPolicy> {

        val simpleRetryPolicy = SimpleRetryPolicy(maxAttemps)
        val neverRetryPolicy = NeverRetryPolicy()

        return Classifier { throwable ->

            when (throwable) {

                is HttpStatusCodeException -> when (httpStatusRetry.contains(throwable.statusCode)) {

                    true -> simpleRetryPolicy
                    false -> neverRetryPolicy
                }

                else -> neverRetryPolicy
            }
        }
    }
}

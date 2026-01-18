package com.myrran.stockator.infrastructure.threadpools

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration @EnableAsync
class ThreadPoolsConfiguration {

    companion object {
        const val ALPHA_VANTAGE_THREAD_POLL = "alphaVantageThreadPoll"
    }

    @Bean(ALPHA_VANTAGE_THREAD_POLL)
    fun riskApiThreadPool(): ThreadPoolTaskExecutor =

        ThreadPoolTaskExecutor().apply {
            corePoolSize = 100
            setWaitForTasksToCompleteOnShutdown(true)
            setThreadNamePrefix("API-AV-")
        }
}

package com.myrran.stockator.infrastructure.spring.cache

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class MapDBCacheProperties(

    @Value("\${cache.mapdb.filepath}")
    val filePath: String,
    @Value("\${cache.mapdb.enabletransactions}")
    val enableTransactions: Boolean,
    @Value("\${cache.mapdb.cleanuponstart}")
    val cleanupOnStart: Boolean,
    @Value("\${cache.mapdb.ttlinhours}")
    val ttlInHours: Long
)

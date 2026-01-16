package com.myrran.stockator.infrastructure.spring.cache

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class MapDBCacheProperties(

    @Value("\${datasource.mapdb.filepath}")
    val filePath: String,
    @Value("\${datasource.mapdb.enabletransactions}")
    val enableTransactions: Boolean,
    @Value("\${datasource.mapdb.cleanuponstart}")
    val cleanupOnStart: Boolean,
)

package com.myrran.stockator.infrastructure.spring.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class MapDBProperties(

    @Value("\${datasource.mapdb.filepath}")
    val filePath: String,
    @Value("\${datasource.mapdb.enabletransactions}")
    val enableTransactions: Boolean,
    @Value("\${datasource.mapdb.cleanuponstart}")
    val cleanupOnStart: Boolean,
    @Value("\${datasource.mapdb.backuponshutdown}")
    val backupOnShutdown: Boolean,
)

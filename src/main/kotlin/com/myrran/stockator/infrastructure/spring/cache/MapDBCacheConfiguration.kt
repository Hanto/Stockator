package com.myrran.stockator.infrastructure.spring.cache

import org.mapdb.DB
import org.mapdb.DBMaker
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File

@EnableCaching
@Configuration
class MapDBCacheConfiguration(

    val properties: MapDBCacheProperties
)
{
    @Bean(destroyMethod = "close")
    fun mapDB(): DB {

        val dbMaker = DBMaker.fileDB(File(properties.filePath))
            .fileMmapEnableIfSupported()
            .closeOnJvmShutdown()
            .checksumHeaderBypass()

        // Enable transactions if configured
        if (properties.enableTransactions)
            dbMaker.transactionEnable()

        // Configure cleanup
        if (properties.cleanupOnStart)
            dbMaker.cleanerHackEnable()

        return dbMaker.make()
    }

    @Bean("mapDBCacheManager")
    fun mapDBcacheManager(mapDB: DB): CacheManager =
        MapDBCacheManager(mapDB)
}

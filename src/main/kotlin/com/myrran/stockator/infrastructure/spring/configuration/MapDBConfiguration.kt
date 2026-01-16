package com.myrran.stockator.infrastructure.spring.configuration

import com.myrran.stockator.infrastructure.repositories.alphavantagemonthlyseries.AVTickerMonthlySeriesEntity
import org.mapdb.DB
import org.mapdb.DBMaker
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File
import java.util.concurrent.ConcurrentMap

@Configuration
class MapDBConfiguration(

    val properties: MapDBProperties
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

    @Bean
    fun tickerMonthlySerieMap(db: DB): ConcurrentMap<String, AVTickerMonthlySeriesEntity> =
        db.hashMap("tickerMonthlySerieMap")
            .keySerializer(org.mapdb.Serializer.STRING)
            .valueSerializer(org.mapdb.Serializer.JAVA)
            .createOrOpen() as ConcurrentMap<String, AVTickerMonthlySeriesEntity>
}

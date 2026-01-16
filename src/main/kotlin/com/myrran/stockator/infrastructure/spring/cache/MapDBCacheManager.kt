package com.myrran.stockator.infrastructure.spring.cache

import org.mapdb.DB
import org.mapdb.HTreeMap
import org.mapdb.Serializer
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import java.util.concurrent.Callable
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

class MapDBCacheManager(

    private val mapDB: DB,

): CacheManager {

    private val caches = ConcurrentHashMap<String, Cache>()

    override fun getCacheNames(): Collection<String> = caches.keys


    override fun getCache(name: String): Cache =
        caches.computeIfAbsent(name) { cacheName -> createMapDBCache(cacheName, mapDB) }

    private fun createMapDBCache(name: String, db: DB): Cache {
        val map: HTreeMap<String, Any> = db.hashMap(name)
            .keySerializer(Serializer.STRING)
            .valueSerializer(Serializer.JAVA)
            .expireAfterCreate(30, TimeUnit.DAYS) // TTL after creation
            .expireAfterUpdate(30, TimeUnit.DAYS) // TTL after update
            .expireAfterGet(30, TimeUnit.DAYS) // Extend TTL on access
            .createOrOpen()

        return MapDBCache(name, map)
    }

    class MapDBCache(

        private val name: String,
        private val map: HTreeMap<String, Any>

    ) : Cache {

        override fun getName(): String = name

        override fun getNativeCache(): Any = map

        override fun get(key: Any): Cache.ValueWrapper? =
            map[key.toString()]?.let { Cache.ValueWrapper { it } }

        @Suppress("UNCHECKED_CAST")
        override fun <T: Any> get(key: Any, type: Class<T>?): T? {
            val value = map[key.toString()]
            return if (type?.isInstance(value) ?: false) value as T else null
        }

        @Suppress("UNCHECKED_CAST")
        override fun <T : Any> get(key: Any, valueLoader: Callable<T>): T =
            map.putIfAbsent(key.toString(), valueLoader.call() as T) as T

        override fun put(key: Any, value: Any?) =
            when (value != null) {
                true -> map[key.toString()] = value
                false -> evict(key)
            }

        override fun putIfAbsent(key: Any, value: Any?): Cache.ValueWrapper? {
            val existing = map.putIfAbsent(key.toString(), value)
            return existing?.let { Cache.ValueWrapper { it } }
        }

        override fun evict(key: Any) {
            map.remove(key.toString())
        }

        override fun clear() {
            map.clear()
        }
    }
}

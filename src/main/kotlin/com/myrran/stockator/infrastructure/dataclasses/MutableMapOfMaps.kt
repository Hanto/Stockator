package com.myrran.stockator.infrastructure.dataclasses

class MutableMapOfMaps<KEY1, KEY2, VALUE>(

    rootMapCreator: () -> MutableMap<KEY1, MutableMap<KEY2, VALUE>>,
    private val innerMapCreator: () -> MutableMap<KEY2, VALUE>,
    private val rootMap: MutableMap<KEY1, MutableMap<KEY2, VALUE>> = rootMapCreator.invoke()

): MutableMap<KEY1, MutableMap<KEY2, VALUE>> by rootMap
{
    operator fun set(key1: KEY1, key2: KEY2, value: VALUE) {

        val innerMap = rootMap.computeIfAbsent(key1) { innerMapCreator.invoke() }
        innerMap[key2] = value
    }

    operator fun get(key1: KEY1, key2: KEY2): VALUE? =

        rootMap[key1]?.get(key2)

}

package com.myrran.stockator.domain.misc

import org.apache.commons.math3.stat.descriptive.rank.Median

// 25% increase = 1.25% Percentage
data class Increase(
    val value: Double
)
{
    operator fun compareTo(other: Increase): Int =
        value.compareTo(other.value)

    operator fun times(percentage: Percentage): Increase =
        Increase(value * percentage.value)

    fun toPercentage(): Percentage =
        Percentage((value + 100) / 100)
}

fun Collection<Increase>.average(): Increase =
    Increase(this.map { it.value }.average())

fun Collection<Increase>.median(): Increase =
    Increase(Median().evaluate(this.map { it.value }.toDoubleArray()) )

package com.myrran.stockator.domain.misc

import org.apache.commons.math3.stat.descriptive.rank.Median

sealed interface IncreaseI

data object IncreaseNaN: IncreaseI
data class Increase(
    val value: Double
): IncreaseI
{
    operator fun compareTo(other: Increase): Int =
        value.compareTo(other.value)

    operator fun times(percentage: Percentage): Increase =
        Increase(value * percentage.value)
}

fun Collection<IncreaseI>.average(): Increase =
    Increase(this.filterIsInstance<Increase>().map { it.value }.average())

fun Collection<IncreaseI>.median(): Increase =
    Increase(Median().evaluate(this.filterIsInstance<Increase>().map { it.value }.toDoubleArray()) )

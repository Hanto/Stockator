package com.myrran.stockator.domain.misc

import org.apache.commons.math3.stat.descriptive.rank.Median

sealed interface IncreaseI {
    fun toPercentage(): PercentageI
}

data object IncreaseNaN: IncreaseI {

    override fun toPercentage(): PercentageI = PercentageNaN
}

// 25% increase = 1.25% Percentage
data class Increase(
    val value: Double
): IncreaseI
{
    operator fun compareTo(other: Increase): Int =
        value.compareTo(other.value)

    operator fun times(percentage: Percentage): Increase =
        Increase(value * percentage.value)

    override fun toPercentage(): Percentage =
        Percentage((value + 100) / 100)
}

fun Collection<IncreaseI>.average(): Increase =
    Increase(this.filterIsInstance<Increase>().map { it.value }.average())

fun Collection<IncreaseI>.median(): Increase =
    Increase(Median().evaluate(this.filterIsInstance<Increase>().map { it.value }.toDoubleArray()) )

package com.myrran.stockator.domain

import org.apache.commons.math3.stat.descriptive.rank.Median

sealed interface PercentageI

data class Percentage(
    val value: Double
): PercentageI
{
    operator fun compareTo(other: Percentage): Int =
        value.compareTo(other.value)

    operator fun times(number: Number): Percentage =
        Percentage(value * number.toDouble())
}

data object PercentageNaN: PercentageI

fun Collection<PercentageI>.average(): Percentage =
    Percentage(this.filterIsInstance<Percentage>().map { it.value }.average())

fun Collection<PercentageI>.median(): Percentage =
    Percentage(Median().evaluate(this.filterIsInstance<Percentage>().map { it.value }.toDoubleArray()) )

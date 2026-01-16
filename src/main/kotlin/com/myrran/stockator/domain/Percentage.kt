package com.myrran.stockator.domain

import org.apache.commons.math3.stat.descriptive.rank.Median


data class Percentage(
    val value: Double
)
{
    operator fun compareTo(other: Percentage): Int =
        value.compareTo(other.value)

    operator fun times(number: Number): Percentage =
        Percentage(value * number.toDouble())
}

fun Collection<Percentage>.average(): Percentage =
    Percentage(this.map { it.value }.average())

fun Collection<Percentage>.median(): Percentage =
    Percentage(Median().evaluate(this.map { it.value }.toDoubleArray()) )


package com.myrran.stockator.domain.misc

// 1.25% Percentage = 25% increase
data class Percentage(
    val value: Double
)
{
    operator fun compareTo(other: Increase): Int =
        value.compareTo(other.value)

    operator fun times(other: Percentage): Percentage =
        Percentage(this.value * other.value)

    fun toIncrease(): Increase =
        Increase((value -1) * 100.0)
}

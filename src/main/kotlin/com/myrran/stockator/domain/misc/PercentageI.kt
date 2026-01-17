package com.myrran.stockator.domain.misc

sealed interface PercentageI {
    fun toIncrease(): IncreaseI
}

data object PercentageNaN: PercentageI {

    override fun toIncrease(): IncreaseI = IncreaseNaN
}

// 1.25% Percentage = 25% increase
data class Percentage(
    val value: Double
): PercentageI {

    operator fun times(other: Percentage): Percentage =
        Percentage(this.value * other.value)

    override fun toIncrease(): Increase =
        Increase((value -1) * 100.0)
}

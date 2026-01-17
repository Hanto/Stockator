package com.myrran.stockator.domain.misc

sealed interface PercentageI {
    fun toIncrease(): IncreaseI
}

data object PercentageNaN: PercentageI {

    override fun toIncrease(): IncreaseI = IncreaseNaN
}

data class Percentage(
    val value: Double
): PercentageI {

    override fun toIncrease(): IncreaseI =
        Increase((value -1) * 100.0)
}

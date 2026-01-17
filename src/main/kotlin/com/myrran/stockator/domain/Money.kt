package com.myrran.stockator.domain

data class Money(
    val amount: Double
)
{
    fun isZero(): Boolean =

        amount == 0.0

    operator fun div(other: Money): PercentageI =

        when (this.isZero() || other.isZero()){
            true -> PercentageNaN
            false -> Percentage(((amount / other.amount) - 1) * 100f)
        }
}

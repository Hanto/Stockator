package com.myrran.stockator.domain.misc

data class Money(
    val amount: Double
)
{
    fun isZero(): Boolean =

        amount == 0.0

    operator fun div(other: Money): Percentage =
        Percentage(amount / other.amount)
}

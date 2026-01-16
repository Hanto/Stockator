package com.myrran.stockator.domain

data class Money(
    val amount: Double
)
{
    operator fun div(other: Money): Percentage =

        Percentage(((amount / other.amount) - 1) * 100f)
}

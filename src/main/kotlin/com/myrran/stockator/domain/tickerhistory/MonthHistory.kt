package com.myrran.stockator.domain.tickerhistory

import com.myrran.stockator.domain.misc.IncreaseI
import com.myrran.stockator.domain.misc.Money
import java.time.LocalDate

class MonthHistory(
    val openingPrice: Money,
    val closingPrice: Money,
    val closingDay: LocalDate
) {
    val increase: IncreaseI =
        when (openingPrice.isZero()) {
            true -> (closingPrice / closingPrice).toIncrease()
            false -> (closingPrice / openingPrice).toIncrease()
        }
}

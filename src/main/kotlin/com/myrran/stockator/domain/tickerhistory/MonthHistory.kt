package com.myrran.stockator.domain.tickerhistory

import com.myrran.stockator.domain.misc.Increase
import com.myrran.stockator.domain.misc.Money
import java.time.LocalDate

class MonthHistory(
    val openingPrice: Money,
    val closingPrice: Money,
    val closingDay: LocalDate
) {
    val increase: Increase = (closingPrice / openingPrice).toIncrease()

    init {
        require(!openingPrice.isZero()) { "Opening price cannot be zero or negative for closingDay: $closingDay" }
        require(!closingPrice.isZero()) { "Closing price cannot be zero or negative for closingDay: $closingDay" }
    }
}

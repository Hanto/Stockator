package com.myrran.stockator.domain.tickerhistory

import com.myrran.stockator.domain.misc.IncreaseI
import com.myrran.stockator.domain.misc.Money
import java.time.LocalDate

class MonthlyRates(
    val openingPrice: Money,
    val closingPrice: Money,
    val closingDay: LocalDate
) {
    val increase: IncreaseI = (closingPrice / openingPrice).toIncrease()
}

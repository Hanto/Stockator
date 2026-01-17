package com.myrran.stockator.domain.tickerseries

import com.myrran.stockator.domain.misc.IncreaseI
import com.myrran.stockator.domain.misc.Money
import java.time.LocalDate

class MonthlyData(
    val openingPrice: Money,
    val closingPrice: Money,
    val closingDay: LocalDate
) {
    val increase: IncreaseI = (closingPrice / openingPrice).toIncrease()
}

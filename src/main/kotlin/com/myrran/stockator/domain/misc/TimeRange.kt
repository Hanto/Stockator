package com.myrran.stockator.domain.misc

import java.time.temporal.ChronoUnit

data class TimeRange(
    val amount: Long,
    val unit: ChronoUnit,
)

package com.myrran.stockator

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<StockatorApplication>().with(TestcontainersConfiguration::class).run(*args)
}

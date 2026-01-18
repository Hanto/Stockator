package com.myrran.stockator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StockatorApplication

fun main(args: Array<String>) {
	runApplication<StockatorApplication>(*args)
}

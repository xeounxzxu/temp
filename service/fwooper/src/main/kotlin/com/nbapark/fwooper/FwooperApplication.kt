package com.nbapark.fwooper

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FwooperApplication

fun main(args: Array<String>) {
    runApplication<FwooperApplication>(*args)
}

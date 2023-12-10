package com.nbapark.griffin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GriffinApplication

fun main(args: Array<String>) {
    runApplication<GriffinApplication>(*args)
}

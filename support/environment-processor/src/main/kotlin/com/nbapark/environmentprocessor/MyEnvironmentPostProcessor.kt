package com.nbapark.environmentprocessor

import org.springframework.boot.SpringApplication
import org.springframework.boot.env.EnvironmentPostProcessor
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order

@Order(Ordered.LOWEST_PRECEDENCE)
class MyEnvironmentPostProcessor : EnvironmentPostProcessor {

    override fun postProcessEnvironment(
        environment: org.springframework.core.env.ConfigurableEnvironment?,
        application: SpringApplication?
    ) {

        return
    }
}

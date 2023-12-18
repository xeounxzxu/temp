package com.nbapark.fwooper.infra.client

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class GriffinConfiguration {

    @Bean
    fun baseClient(): WebClient = WebClient
        .builder()
        .baseUrl("http://localhost:9090")
        .build()

    @Bean
    fun griffinClient(baseClient: WebClient): GriffinClient = HttpServiceProxyFactory
        .builderFor(WebClientAdapter.create(baseClient))
        .build()
        .createClient(GriffinClient::class.java)
}

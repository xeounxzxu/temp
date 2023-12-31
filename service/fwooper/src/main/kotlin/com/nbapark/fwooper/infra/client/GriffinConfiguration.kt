package com.nbapark.fwooper.infra.client

import com.nbapark.exception.dto.ErrorMessage
import com.nbapark.exception.global.ErrorCode
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatusCode
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import reactor.util.retry.Retry
import java.time.Duration

private val logger = KotlinLogging.logger { }

@Configuration
class GriffinConfiguration {

    @Bean
    fun baseClient(): WebClient =
        WebClient.builder()
            .baseUrl("http://localhost:9090")
            .filter(retryFilter())
            .defaultStatusHandler(
                HttpStatusCode::isError
            ) { res ->
                res.bodyToFlux(ErrorMessage::class.java)
                    .next()
                    .handle { it, sink ->
                        when {
                            res.statusCode().is4xxClientError -> sink.error(WebClient4xxException(it.message))
                            else -> sink.error(WebClient5xxException(it.message))
                        }
                    }
            }.build()

    @Bean
    fun griffinClient(baseClient: WebClient): GriffinClient =
        HttpServiceProxyFactory
            .builderFor(WebClientAdapter.create(baseClient)).build()
            .createClient(GriffinClient::class.java)


    private fun retryFilter() = ExchangeFilterFunction { request, next ->
        next.exchange(request)
            .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(30)).doAfterRetry {
                logger.debug {
                    "catch retry"
                }
            })
    }

}


class WebClient4xxException(message: String?, throwable: Throwable? = null) :
    WebClientRuntimeException(ErrorCode.E001, message, throwable)

class WebClient5xxException(message: String?, throwable: Throwable? = null) :
    WebClientRuntimeException(ErrorCode.E002, message, throwable)

sealed class WebClientRuntimeException(
    errorCode: ErrorCode,
    message: String?,
    cause: Throwable?,
) : RuntimeException(message, cause) {

    val code = errorCode
}

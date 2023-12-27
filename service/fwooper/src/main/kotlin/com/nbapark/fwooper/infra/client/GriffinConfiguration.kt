package com.nbapark.fwooper.infra.client

import com.nbapark.fwooper.core.exception.ErrorCode
import com.nbapark.fwooper.core.exception.ErrorMessage
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.ExchangeFunction
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration
import java.util.function.Function

// fixme : logger checked ..
//private val log = KotlinLogging.logger {}

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
        HttpServiceProxyFactory.builderFor(WebClientAdapter.create(baseClient)).build()
            .createClient(GriffinClient::class.java)


    private fun retryFilter() = ExchangeFilterFunction { request, next ->
        next.exchange(request).retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(30)).doAfterRetry {
            println("retry filter")
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

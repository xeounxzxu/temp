package com.nbapark.griffin.core.exception

import com.nbapark.exception.dto.ErrorMessage
import com.nbapark.exception.global.ErrorCode
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

private val logger = KotlinLogging.logger { }

@Order(-2)
@Component
class GlobalErrorExceptionHandler(
    errorAttributes: ErrorAttributes,
    applicationContext: ApplicationContext,
    serverCodecConfigurer: ServerCodecConfigurer
) : AbstractErrorWebExceptionHandler(
    errorAttributes,
    WebProperties.Resources(),
    applicationContext
) {

    init {
        super.setMessageWriters(serverCodecConfigurer.writers)
        super.setMessageReaders(serverCodecConfigurer.readers)
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes): RouterFunction<ServerResponse> {
        return RouterFunctions.route(RequestPredicates.all(), errorServerResponse)
    }

    private val errorServerResponse = HandlerFunction { request ->
        val throwable = super.getError(request)
        logger.error { throwable }
        when (throwable) {
            is IllegalArgumentException -> {
                ErrorCode.E002.toServerResponse(throwable)
            }

            else -> {
                ErrorCode.E001.toServerResponse(throwable)
            }
        }
    }

}

fun ErrorCode.toServerResponse(e: Throwable?): Mono<ServerResponse> {
    return ServerResponse.status(this.status)
        .bodyValue(
            ErrorMessage(
                this.status,
                this.code,
                e?.message ?: "",
                e?.localizedMessage ?: ""
            )
        )
}

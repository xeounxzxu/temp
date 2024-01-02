package com.nbapark.griffin.core.exception

import mu.KotlinLogging
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

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
        return RouterFunctions.route(RequestPredicates.all(), getError2)
    }

    private val getError2 = HandlerFunction { request ->
        val throwable = super.getError(request)
        // TODO : Custom Exception Add
        logger.error { throwable }
        when (throwable) {
            is IllegalArgumentException -> {
                HttpStatus.BAD_REQUEST
                    .run {
                        ServerResponse.status(this)
                            .bodyValue(
                                ErrorMessage(
                                    this.value(),
                                    throwable.message ?: "",
                                    throwable.localizedMessage
                                )
                            )
                    }
            }

            // FIXME : after remove case
            is Exception -> {
                HttpStatus.INTERNAL_SERVER_ERROR
                    .run {
                        ServerResponse.status(this)
                            .bodyValue(
                                ErrorMessage(
                                    this.value(),
                                    throwable.message ?: "",
                                    throwable.localizedMessage
                                )
                            )
                    }
            }

            else -> {
                HttpStatus.INTERNAL_SERVER_ERROR
                    .run {
                        ServerResponse.status(this)
                            .bodyValue(
                                ErrorMessage(
                                    this.value(),
                                    throwable.message ?: "",
                                    throwable.localizedMessage
                                )
                            )
                    }
            }
        }
    }

}

data class ErrorMessage(
    val status: Int,
    val message: String,
    val detail: String
)

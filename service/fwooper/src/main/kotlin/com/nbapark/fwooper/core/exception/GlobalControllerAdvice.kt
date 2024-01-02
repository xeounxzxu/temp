package com.nbapark.fwooper.core.exception

import com.nbapark.exception.dto.ErrorMessage
import com.nbapark.exception.global.ErrorCode
import com.nbapark.exception.global.toResponseEntity
import com.nbapark.fwooper.infra.client.WebClientRuntimeException
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

private val logger = KotlinLogging.logger { }

@RestControllerAdvice
class GlobalControllerAdvice {

    @ExceptionHandler(
        value = [
            RuntimeException::class,
            IllegalStateException::class
        ]
    )
    fun error(e: RuntimeException): ResponseEntity<ErrorMessage> =
        ErrorCode.E001.toResponseEntity(e.message ?: "")
            .apply {
                logger.error { e }
            }

    @ExceptionHandler(
        value = [
            IllegalArgumentException::class
        ]
    )
    fun error(e: IllegalArgumentException): ResponseEntity<ErrorMessage> =
        ErrorCode.E002.toResponseEntity(e.message ?: "")
            .apply {
                logger.error { e }
            }

    @ExceptionHandler(
        value = [
            WebClientRuntimeException::class
        ]
    )
    fun error(e: WebClientRuntimeException) = e.code.toResponseEntity(e.message ?: "")
        .apply {
            logger.error { e }
        }
}




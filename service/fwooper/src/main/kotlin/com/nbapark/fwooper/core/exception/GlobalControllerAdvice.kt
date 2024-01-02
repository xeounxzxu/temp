package com.nbapark.fwooper.core.exception

import com.nbapark.fwooper.infra.client.WebClientRuntimeException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
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

enum class ErrorCode(
    val status: Int,
    val code: String,
    val message: String
) {

    E001(HttpStatus.INTERNAL_SERVER_ERROR.value(), "E001", "알수없는에러"),
    E002(HttpStatus.BAD_REQUEST.value(), "E002", "요청에러"),
    E003(HttpStatus.BAD_GATEWAY.value(), "E003", "요청된에러가아닙니다.")
}

fun ErrorCode.toResponseEntity(detail: String): ResponseEntity<ErrorMessage> =
    toErrorMessage(detail).run { ResponseEntity.status(this.status).body(this) }

private fun ErrorCode.toErrorMessage(detail: String) = ErrorMessage(this.status, this.code, this.message, detail)

data class ErrorMessage(
    val status: Int,
    val code: String?,
    val message: String,
    val detail: String
)


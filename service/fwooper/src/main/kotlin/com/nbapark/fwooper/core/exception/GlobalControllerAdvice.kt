package com.nbapark.fwooper.core.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalControllerAdvice {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException::class)
    fun error(e: RuntimeException): ErrorMessage = ErrorMessage(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "E001",
        "알수없는에러입니다",
        e.message.toString()
    )

}

data class ErrorMessage(
    val status: Int,
    val code: String?,
    val message: String,
    val detail: String
)


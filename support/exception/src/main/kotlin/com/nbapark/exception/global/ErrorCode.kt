package com.nbapark.exception.global

import com.nbapark.exception.dto.ErrorMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

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


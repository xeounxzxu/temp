package com.nbapark.fwooper.core.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.nbapark.fwooper.core.exception.ErrorCode
import com.nbapark.fwooper.core.exception.ErrorMessage
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

private const val USER_ID = "X-USER-ID"

fun HttpServletRequest.getUserId(): String {
    return this.getHeader(USER_ID) ?: throw NullPointerException("Header $USER_ID 를 확인하세요")
}

fun HttpServletResponse.setErrorMessage(errorCode: ErrorCode, om: ObjectMapper, ex: Throwable?) {
    this.status = errorCode.status
    this.contentType = "application/json; charset=utf-8"
    val message = om.writeValueAsString(
        ErrorMessage(
            errorCode.status,
            errorCode.code,
            errorCode.message,
            ex?.message.toString()
        )
    )
    this.writer.write(message)
}

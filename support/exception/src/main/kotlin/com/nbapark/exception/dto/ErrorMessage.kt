package com.nbapark.exception.dto

data class ErrorMessage(
    val status: Int,
    val code: String?,
    val message: String,
    val detail: String
)

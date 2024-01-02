package com.nbapark.fwooper.core.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.nbapark.fwooper.core.exception.ErrorCode
import com.nbapark.fwooper.core.exception.ErrorMessage
import com.nbapark.fwooper.core.utils.MDCUtils
import com.nbapark.fwooper.infra.client.GriffinClient
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException


@Component
class PrivacyFilter(
    private val griffinClient: GriffinClient,
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {

            // todo : logging add & change ... exception
            val userId = request.getHeader(USER_ID) ?: throw RuntimeException("bad gateway")

            val userInfo = griffinClient.getUser(userId.toLong())

            MDCUtils.setUserId(userInfo.userId)
            MDCUtils.setUserNickName(userInfo.nickName)

            filterChain.doFilter(request, response)

        } catch (e: RuntimeException) {
            logger.error(e)
            setErrorResponse(ErrorCode.E003, response, e)
        }
    }

    fun setErrorResponse(code: ErrorCode, response: HttpServletResponse, ex: Throwable?) {
        response.status = code.status
        response.contentType = "application/json"
        try {
            val message = objectMapper.writeValueAsString(
                ErrorMessage(
                    code.status,
                    code.code,
                    code.message,
                    ex!!.message.toString()
                )
            )
            response.writer.write(message)
        } catch (e: IOException) {
            logger.error(e)
        }
    }

    companion object {
        private const val USER_ID = "X-USER-ID"
    }
}

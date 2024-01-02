package com.nbapark.fwooper.core.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.nbapark.exception.global.ErrorCode
import com.nbapark.fwooper.core.utils.MDCUtils
import com.nbapark.fwooper.core.utils.getUserId
import com.nbapark.fwooper.core.utils.setErrorMessage
import com.nbapark.fwooper.infra.client.GriffinClient
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


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

            val userId = request.getUserId()

            val userInfo = griffinClient.getUser(userId.toLong())

            MDCUtils.setUserId(userInfo.userId)
            MDCUtils.setUserNickName(userInfo.nickName)

            filterChain.doFilter(request, response)

            MDCUtils.clear()

        } catch (e: RuntimeException) {
            logger.error(e)
            response.setErrorMessage(ErrorCode.E003, objectMapper, e)
        }
    }
}

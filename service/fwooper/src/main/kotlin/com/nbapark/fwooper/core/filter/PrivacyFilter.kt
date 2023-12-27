package com.nbapark.fwooper.core.filter

import com.nbapark.fwooper.infra.client.GriffinClient
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException


@Component
class PrivacyFilter(
//    private val griffinClient: GriffinClient
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {

            // todo : logging add & change ... exception
            // todo : checked to user ...
//            val userId = request.getHeader(USER_ID) ?: throw RuntimeException("bad gateway")

            filterChain.doFilter(request, response)

        } catch (e: RuntimeException) {
            // todo : logging add & change ... exception
            // e.printStackTrace()
            setErrorResponse(HttpStatus.BAD_GATEWAY, response, e)
        }
    }

    fun setErrorResponse(status: HttpStatus, response: HttpServletResponse, ex: Throwable?) {
        response.status = status.value()
        response.contentType = "application/json"
        try {
            response.writer.write("{\"error\" : \"badgateway\"}")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val USER_ID = "X-USER-ID"
    }
}

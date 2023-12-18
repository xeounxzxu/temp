package com.nbapark.fwooper.infra.client

import com.nbapark.fwooper.infra.client.dto.UserInfoDto
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange

@HttpExchange("/v1/users")
interface GriffinClient {

    @GetExchange("/{userId}")
    fun getUser(@PathVariable userId: Long): UserInfoDto
}

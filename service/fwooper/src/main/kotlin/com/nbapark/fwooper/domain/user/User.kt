package com.nbapark.fwooper.domain.user

import com.nbapark.fwooper.infra.client.GriffinClient
import org.springframework.stereotype.Component

@Component
class UserValidation(
    private val griffinClient: GriffinClient
) {

    fun valid(userId: Long) {
        griffinClient.getUser(userId)
    }
}

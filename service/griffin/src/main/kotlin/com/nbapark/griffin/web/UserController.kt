package com.nbapark.griffin.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class UserController {
    @GetMapping("/users/{userId}")
    suspend fun getUserInfo(
        @PathVariable userId: Long,
    ): Map<String, Any> =
        when (userId) {
            100L -> throw RuntimeException("100은에러")
            101L -> throw IllegalArgumentException("101은에러")
            else -> mapOf(
                "userId" to userId,
                "nickName" to "테스트-$userId"
            )
        }
}

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
        if (100L == userId) {
            // todo : 100 유저 Mock 예외 처리
            throw RuntimeException("100 은 예외 유저 입니다.")
        } else {
            mapOf(
                "userId" to userId,
                "nikeName" to "테스트-$userId",
            )
        }
}

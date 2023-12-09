package com.nbapark.fwooper.web.v1

import com.nbapark.fwooper.dto.PostSaveRequest
import com.nbapark.fwooper.dto.toCommand
import com.nbapark.fwooper.service.PostService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("v1")
@RestController
class PostController(
    private val service: PostService
) {

    @PostMapping("post")
    fun write(@RequestBody request: PostSaveRequest) {
        service.write(request.toCommand())
    }
}

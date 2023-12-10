package com.nbapark.fwooper.web.v1

import com.nbapark.app.rest.V1Controller
import com.nbapark.fwooper.dto.PostSaveRequest
import com.nbapark.fwooper.dto.toCommand
import com.nbapark.fwooper.service.PostService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@V1Controller
class PostController(
    private val service: PostService
) {

    @PostMapping("post")
    fun write(@RequestBody request: PostSaveRequest) {
        service.write(request.toCommand())
    }
}

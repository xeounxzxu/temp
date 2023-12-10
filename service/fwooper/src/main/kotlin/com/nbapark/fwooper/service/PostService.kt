package com.nbapark.fwooper.service

import com.nbapark.fwooper.domain.PostRepository
import com.nbapark.fwooper.dto.PostSaveCommand
import com.nbapark.fwooper.dto.PostSaveResult
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
) {
    @Transactional
    fun write(command: PostSaveCommand): PostSaveResult {
        return postRepository.save(command)
    }
}

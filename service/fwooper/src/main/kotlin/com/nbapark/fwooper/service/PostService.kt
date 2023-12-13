package com.nbapark.fwooper.service

import com.nbapark.fwooper.domain.Post
import com.nbapark.fwooper.domain.PostRepository
import com.nbapark.fwooper.domain.write
import com.nbapark.fwooper.dto.PostSaveCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
) {
    @Transactional
    fun write(command: PostSaveCommand): Post {
        val post = Post(command.title, command.content)
        return post.write(repository = postRepository)
    }
}

package com.nbapark.fwooper.service

import com.nbapark.fwooper.domain.post.Post
import com.nbapark.fwooper.domain.post.PostRepository
import com.nbapark.fwooper.domain.post.write
import com.nbapark.fwooper.domain.user.UserValidation
import com.nbapark.fwooper.dto.PostSaveCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userValidation: UserValidation
) {
    @Transactional
    fun write(command: PostSaveCommand): Post {

        userValidation.valid(command.userId)

        val post = Post(command.title, command.content)
        return post.write(repository = postRepository)
    }
}

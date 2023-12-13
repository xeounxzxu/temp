package com.nbapark.fwooper.domain

import org.springframework.stereotype.Repository

@Repository
interface PostRepository {
    fun save(post: Post): Post
}


package com.nbapark.fwooper.domain.post


interface PostRepository {
    fun save(post: Post): Post
}


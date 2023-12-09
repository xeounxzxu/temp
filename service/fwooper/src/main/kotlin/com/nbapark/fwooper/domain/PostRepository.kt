package com.nbapark.fwooper.domain

import com.nbapark.fwooper.dto.PostSaveCommand
import com.nbapark.fwooper.dto.PostSaveResult
import com.nbapark.fwooper.dto.toEntity
import com.nbapark.fwooper.infra.PostJpaRepository
import org.springframework.stereotype.Repository

interface PostRepository {
    fun save(command: PostSaveCommand): PostSaveResult
}

@Repository
class PostRepositoryImpl(
    private val postJpaRepository: PostJpaRepository
) : PostRepository {

    override fun save(command: PostSaveCommand): PostSaveResult {
        postJpaRepository.save(command.toEntity())
        return PostSaveResult("", "", "")
    }
}

package com.nbapark.fwooper.dto

import com.nbapark.fwooper.infra.PostDataModel

data class PostSaveRequest(
    val title: String,
    val content: String,
    val userId: Long,
)

fun PostSaveRequest.toCommand(): PostSaveCommand = PostSaveCommand(this.title, this.content, this.userId)

data class PostSaveCommand(
    val title: String,
    val content: String,
    val userId: Long,
)

fun PostSaveCommand.toEntity(): PostDataModel = PostDataModel(title, content)

data class PostSaveResult(
    val title: String,
    val content: String,
    val userName: String,
)

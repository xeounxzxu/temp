package com.nbapark.fwooper.domain.post

import java.time.LocalDateTime

data class Post(
    val title: String,
    val content: String,
    // 작성자
    val createdBy: String,
    val createdDate: LocalDateTime?,
    val updatedDateTime: LocalDateTime?
) {

    constructor(title: String, content: String) : this(title, content, "", null, null)
}

// 다이어그램
// 먼저 유저 조회 후 아이디 값 검증 및 추출  ==> Mono Cache
// 추출 후 저장 ,,, !!
fun Post.write(repository: PostRepository) = repository.save(this)

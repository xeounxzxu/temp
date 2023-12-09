package com.nbapark.fwooper.infra

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity(name = "post")
class PostJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?, // id
    @Column(nullable = false)
    val title: String, // 제목
    @Column(nullable = false)
    val content: String, // 컨텐츠
    @CreatedBy
    val createdBy: Long?, // 작성자 번호
    @CreationTimestamp
    val createdDate: LocalDateTime?, // 작성일
    @LastModifiedDate
    val updatedDate: LocalDateTime? // 수정일
) {
    constructor(title: String, content: String) : this(null, title, content, null, null, null)
}

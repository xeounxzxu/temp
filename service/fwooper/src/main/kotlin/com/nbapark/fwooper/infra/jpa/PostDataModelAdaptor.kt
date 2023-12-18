package com.nbapark.fwooper.infra.jpa

import com.nbapark.fwooper.domain.Post
import com.nbapark.fwooper.domain.PostRepository

class PostDataModelAdaptor(
    private val postDataModelRepository: PostDataModelRepository
) : PostRepository {

    override fun save(post: Post): Post = postDataModelRepository.save(toDataModel(post))
        .run { toDomain(this) }

    private fun toDataModel(entity: Post) =
        PostDataModel(entity.title, entity.content)

    private fun toDomain(model: PostDataModel) =
        Post(model.title, model.content, model.id.toString(), checkNotNull(model.createdDate), model.updatedDate)
}

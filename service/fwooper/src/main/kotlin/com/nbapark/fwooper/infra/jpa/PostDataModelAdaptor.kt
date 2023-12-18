package com.nbapark.fwooper.infra.jpa

import com.nbapark.fwooper.domain.post.Post
import com.nbapark.fwooper.domain.post.PostRepository
import org.springframework.stereotype.Repository

// fixme : why not use to interface ?
@Repository
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

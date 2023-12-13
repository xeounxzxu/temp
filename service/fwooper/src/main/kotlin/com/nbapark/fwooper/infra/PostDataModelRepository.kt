package com.nbapark.fwooper.infra

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostDataModelRepository : JpaRepository<PostDataModel, Long>
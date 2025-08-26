package io.anna.annsblog.blog

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : MongoRepository<PostDocument, ObjectId> {
    fun findBySlug(slug: String): PostDocument?
    fun deleteBySlug(slug: String)
    fun findAllByOrderByDateDesc(): List<PostDocument>
}

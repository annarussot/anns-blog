package io.anna.annsblog.blog

import com.mongodb.DuplicateKeyException
import org.springframework.stereotype.Service

@Service
class BlogService (
    private val postRepo: PostRepository
){
    fun createPost(title: String, content: String): PostDocument {
        try {
            val newPost = PostDocument(title = title, content = content)
            print(newPost.slug)
            return postRepo.save(newPost)
        } catch (e: DuplicateKeyException) {
            throw IllegalArgumentException("A post with the same title already exists.")
        }
    }

    fun updatePost(slug: String, content: String): PostDocument? {
        val foundPost = postRepo.findBySlug(slug)
        if (foundPost != null) {
            foundPost.content = content
            postRepo.save(foundPost)
        }
        return foundPost
    }

    fun getAllPosts(): List<PostDocument> {
        return postRepo.findAllByOrderByDateDesc()
    }

    fun getPostBySlug(slug: String): PostDocument? {
        val foundPost = postRepo.findBySlug(slug)
        return foundPost
    }

    fun deletePost(slug: String): Boolean {
        val foundPost = postRepo.findBySlug(slug)
        if (foundPost != null) {
            postRepo.deleteBySlug(slug)
            return true
        }
        return false
    }
}

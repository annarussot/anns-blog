package io.anna.annsblog.blog

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/blog")
class BlogController (
    private val blogService: BlogService
){
    //TODO add pagination
    @GetMapping("")
    fun blog() = "Blog"

    @GetMapping("/{slug}")
    @ResponseBody
    fun blogPost(@PathVariable slug: String) : PostDocument?
    {
        return blogService.getPostBySlug(slug)
    }

    data class BlogPostRequest(
        val title: String,
        val content: String
    )

    @PostMapping("/new")
    @ResponseBody
    fun newPost(@RequestBody request: BlogPostRequest): ResponseEntity<String> {
        print(request)
        return try {
            val doc = blogService.createPost(title = request.title, content = request.content)
            ResponseEntity.ok("Post created successfully: ${doc.slug}")
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @DeleteMapping("/delete/{slug}")
    @ResponseBody
    fun deletePost(@PathVariable slug: String){
        if(blogService.deletePost(slug)){
            ResponseEntity.ok("Post deleted successfully")
        } else {
            ResponseEntity.notFound().build()
        }

    }
}
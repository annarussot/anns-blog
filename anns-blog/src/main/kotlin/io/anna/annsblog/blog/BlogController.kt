package io.anna.annsblog.blog

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/blog", "/blog/")
class BlogController(
    private val blogService: BlogService
) {
    //TODO add private and public pages
    //TODO add flash attributes
    @GetMapping("")
    fun blog(model: Model): String {
        model.addAttribute("posts", blogService.getAllPosts())
        return "blog"
    }

    @GetMapping("/{slug}")
    fun blogPost(model: Model, @PathVariable slug: String): String {
        val doc = blogService.getPostBySlug(slug) ?: return "redirect:/blog?error=Post not found"
        model.addAttribute("post", doc)
        return "blog-post"
    }

    @GetMapping("/new")
    fun newPostForm(): String = "blog-new"

    data class BlogPostRequest(
        val title: String,
        val content: String
    )

    @PostMapping("/new")
    fun newPost(@ModelAttribute request: BlogPostRequest): String {
        print(request)
        try {
            val doc = blogService.createPost(title = request.title, content = request.content)
            return "redirect:/blog/${doc.slug}"
        } catch (e: IllegalArgumentException) {
            return "redirect:/blog?error=${e.message}"
        }
    }

    @DeleteMapping("/delete/{slug}")
    fun deletePost(@PathVariable slug: String): String {
        val post = blogService.getPostBySlug(slug) ?: return "redirect:/blog?error=Post not found"
        return if (blogService.deletePost(slug)) {
            "redirect:/blog?deleted=Post ${post.title} deleted"
        } else
            "redirect:/blog?error=Post not found"
    }
}
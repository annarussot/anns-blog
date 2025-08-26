package io.anna.annsblog.blog

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/blog")
class BlogController (
    private val blogService: BlogService
){
    //TODO add pagination
    @GetMapping("")
    fun blog() = "Blog"

    @GetMapping("/{slug}")
    fun blogPost(@PathVariable slug: String)
    {
        blogService.getPostBySlug(slug)
    }

    @PostMapping("/new")
    @ResponseBody
    fun newPost(): ResponseEntity<String> {
        return ResponseEntity.ok("New post created")
    }
}
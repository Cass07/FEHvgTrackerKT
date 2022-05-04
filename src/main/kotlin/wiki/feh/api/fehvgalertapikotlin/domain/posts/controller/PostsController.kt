package wiki.feh.api.fehvgalertapikotlin.domain.posts.controller

import org.springframework.web.bind.annotation.*
import wiki.feh.api.fehvgalertapikotlin.domain.posts.dto.PostsSaveRequestDto
import wiki.feh.api.fehvgalertapikotlin.domain.posts.dto.PostsUpdateRequestDto
import wiki.feh.api.fehvgalertapikotlin.domain.posts.service.PostsService

@RestController
class PostsController(
    val postsService: PostsService
) {

    @PostMapping("/api/v1/posts")
    fun save(@RequestBody requestDto: PostsSaveRequestDto): Long {
        println("test")
        return postsService.save(requestDto)
    }

    @PutMapping("/api/v1/posts/{id}")
    fun update(@PathVariable id: Long, @RequestBody requestDto: PostsUpdateRequestDto): Long {
        return postsService.update(id, requestDto)
    }

    @DeleteMapping("/api/v1/posts/{id}")
    fun delete(@PathVariable id: Long): Long {
        postsService.delete(id)
        return id
    }
}
package wiki.feh.api.fehvgalertapikotlin.domain.posts.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import wiki.feh.api.fehvgalertapikotlin.domain.posts.dto.PostsSaveRequestDto
import wiki.feh.api.fehvgalertapikotlin.domain.posts.service.PostsService

@RestController
class PostsController (
    val postsService: PostsService
    ) {

    @PostMapping("/api/v1/posts")
    fun save (@RequestBody requestDto: PostsSaveRequestDto) : Long{
        println("test")
        return postsService.save(requestDto)
    }
}
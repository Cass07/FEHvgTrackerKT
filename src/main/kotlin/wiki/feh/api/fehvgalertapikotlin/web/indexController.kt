package wiki.feh.api.fehvgalertapikotlin.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import wiki.feh.api.fehvgalertapikotlin.domain.posts.dto.PostsListResponseDto
import wiki.feh.api.fehvgalertapikotlin.domain.posts.service.PostsService

@RestController
class indexController(
    val postsService: PostsService
) {
    @GetMapping("/hello")
    fun getpost(): List<PostsListResponseDto> {
        return postsService.findAllDecs();
    }
}
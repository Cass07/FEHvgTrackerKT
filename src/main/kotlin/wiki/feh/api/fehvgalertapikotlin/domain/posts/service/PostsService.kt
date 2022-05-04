package wiki.feh.api.fehvgalertapikotlin.domain.posts.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wiki.feh.api.fehvgalertapikotlin.domain.posts.dto.PostsListResponseDto
import wiki.feh.api.fehvgalertapikotlin.domain.posts.dto.PostsSaveRequestDto
import wiki.feh.api.fehvgalertapikotlin.domain.posts.repository.PostsQueryRepository
import wiki.feh.api.fehvgalertapikotlin.domain.posts.repository.PostsRepository

@Service
class PostsService(
    val postsRepository: PostsRepository,
    val postsQueryRepository: PostsQueryRepository
) {

    @Transactional(readOnly = true)
    fun findAllDecs(): List<PostsListResponseDto> {
        return postsQueryRepository.findAllDecs().map { entity -> PostsListResponseDto(entity) }
    }

    @Transactional
    fun save(requestDto: PostsSaveRequestDto) : Long{
        return postsRepository.save(requestDto.toEntity()).id
    }

}
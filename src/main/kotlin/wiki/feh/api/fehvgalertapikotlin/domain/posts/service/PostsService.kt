package wiki.feh.api.fehvgalertapikotlin.domain.posts.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wiki.feh.api.fehvgalertapikotlin.domain.posts.domain.Posts
import wiki.feh.api.fehvgalertapikotlin.domain.posts.dto.*
import wiki.feh.api.fehvgalertapikotlin.domain.posts.repository.PostsQueryRepository
import wiki.feh.api.fehvgalertapikotlin.domain.posts.repository.PostsRepository

@Service
class PostsService(
    val postsRepository: PostsRepository,
    val postsQueryRepository: PostsQueryRepository
) {

    @Transactional(readOnly = true)
    fun findbyId(id: Long): PostsResponseDto {
        val entity: Posts = postsRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("해당 게시글이 없습니다. id = $id")
        return PostsResponseDto(entity = entity)
    }

    @Transactional(readOnly = true)
    fun findAllDecs(): List<PostsListResponseDto> {
        return postsQueryRepository.findAllDecs().map { entity -> PostsListResponseDto(entity) }
    }

    @Transactional(readOnly = true)
    fun findAllDecsPage(page: Int): Page<PostsListResponseDto> {
        return postsRepository.findAll(PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "id"))).map { entity -> PostsListResponseDto(entity) }
    }

    @Transactional
    fun save(requestDto: PostsSaveRequestDto): Long {
        return postsRepository.save(requestDto.toEntity()).id
    }

    @Transactional
    fun update(id: Long, requestDto: PostsUpdateRequestDto): Long {
        val posts: Posts = postsRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("해당 게시글이 없습니다. id = $id")
        posts.update(title = requestDto.title, content = requestDto.content)

        return id
    }

    @Transactional(readOnly = true)
    fun getbyId(id: Long): PostsGetDto? {
        val entity: Posts = postsRepository.findByIdOrNull(id) ?: return null
        return PostsGetDto(entity)
    }


    @Transactional(readOnly = true)
    fun getByIdWithPic(id: Long): PostsGetWithPicDto? {
        val entity: List<PostsGetWithPicDto> = postsQueryRepository.getPostsWithPicture(id)
        return if (entity.isNotEmpty()) {
            entity[0]
        } else {
            null
        }
    }

    @Transactional
    fun delete(id: Long) {
        postsRepository.findById(id).ifPresent(postsRepository::delete)
    }

}
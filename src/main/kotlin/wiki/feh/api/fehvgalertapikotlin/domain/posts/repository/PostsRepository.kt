package wiki.feh.api.fehvgalertapikotlin.domain.posts.repository

import org.springframework.data.jpa.repository.JpaRepository
import wiki.feh.api.fehvgalertapikotlin.domain.posts.domain.Posts

interface PostsRepository : JpaRepository<Posts, Long> {
}
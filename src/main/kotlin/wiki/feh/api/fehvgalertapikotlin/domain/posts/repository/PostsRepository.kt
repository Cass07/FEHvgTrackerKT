package wiki.feh.api.fehvgalertapikotlin.domain.posts.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import wiki.feh.api.fehvgalertapikotlin.domain.posts.domain.Posts

@Repository
interface PostsRepository : JpaRepository<Posts, Long> {
}
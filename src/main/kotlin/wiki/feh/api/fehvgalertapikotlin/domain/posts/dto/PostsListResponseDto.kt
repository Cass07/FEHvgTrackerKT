package wiki.feh.api.fehvgalertapikotlin.domain.posts.dto

import wiki.feh.api.fehvgalertapikotlin.domain.posts.domain.Posts
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PostsListResponseDto(entity: Posts) {
    val id: Long?
    val title: String?
    val author: String?
    val modifiedDate: LocalDateTime
    val modifiedDateString: String

    init {
        this.id = entity.id
        this.title = entity.title
        this.author = entity.author
        this.modifiedDate = entity.modifiedDate
        this.modifiedDateString = this.modifiedDate.format(DateTimeFormatter.ofPattern("MM/dd HH:mm:ss"))
    }
}
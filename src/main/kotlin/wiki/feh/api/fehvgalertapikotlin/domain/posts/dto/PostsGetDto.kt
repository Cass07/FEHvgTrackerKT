package wiki.feh.api.fehvgalertapikotlin.domain.posts.dto

import wiki.feh.api.fehvgalertapikotlin.domain.posts.domain.Posts
import java.time.LocalDateTime

class PostsGetDto(entity: Posts) {

    var id: Long = 0
        private set
    var title: String? = null
        private set
    var author: String? = null
        private set
    var content: String? = null
        private set
    var createDate: LocalDateTime = LocalDateTime.MIN
        private set
    var modifiedDate: LocalDateTime = LocalDateTime.MIN
        private set

    init {
        this.id = entity.id
        this.title = entity.title
        this.author = entity.author
        this.content = entity.content
        this.createDate = entity.createDate
        this.modifiedDate = entity.modifiedDate
    }

}
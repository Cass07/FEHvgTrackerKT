package wiki.feh.api.fehvgalertapikotlin.domain.posts.dto

import wiki.feh.api.fehvgalertapikotlin.domain.posts.domain.Posts
import java.time.LocalDateTime

class PostsGetWithPicDto(id: Long, title: String, content: String, author: String, picture: String, name: String, createDate: LocalDateTime, modifiedDate: LocalDateTime) {


    var id: Long = id
        private set
    var title: String? = title
        private set
    var author: String? = author
        private set
    var content: String? = content
        private set
    var picture: String = picture
        private set
    var name: String = name
        private set
    var createDate: LocalDateTime = createDate
        private set
    var modifiedDate: LocalDateTime = modifiedDate
        private set

}
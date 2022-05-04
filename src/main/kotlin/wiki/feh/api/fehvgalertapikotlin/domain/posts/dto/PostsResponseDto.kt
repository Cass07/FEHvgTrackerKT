package wiki.feh.api.fehvgalertapikotlin.domain.posts.dto

import wiki.feh.api.fehvgalertapikotlin.domain.posts.domain.Posts

class PostsResponseDto (entity: Posts) {

    var id: Long = entity.id
    private set
    var title : String = entity.title
    private set
    var content: String = entity.content
    private set
    var author: String = entity.author
    private set

}
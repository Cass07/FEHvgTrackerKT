package wiki.feh.api.fehvgalertapikotlin.domain.posts.dto

import wiki.feh.api.fehvgalertapikotlin.domain.posts.domain.Posts

class PostsSaveRequestDto(val title: String, val content: String, val author: String) {

    fun toEntity() : Posts {
        return Posts(title = title, content = content, author = author)
    }
}
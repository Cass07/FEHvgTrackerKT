package wiki.feh.api.fehvgalertapikotlin.domain.posts.domain

import wiki.feh.api.fehvgalertapikotlin.domain.BaseTimeEntity
import javax.persistence.*

@Entity
class Posts(
    title: String,
    content: String,
    author: String) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(length = 500, nullable = false)
    var title: String = title
        protected set

    @Column(columnDefinition = "TEXT", nullable = false)
    var content: String = content
        protected set

    var author: String = author
        protected set

    fun update(title: String, content: String) {
        this.title = title
        this.content = content
    }

}


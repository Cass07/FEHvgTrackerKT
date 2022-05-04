package wiki.feh.api.fehvgalertapikotlin.domain.posts.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import wiki.feh.api.fehvgalertapikotlin.domain.posts.domain.Posts
import wiki.feh.api.fehvgalertapikotlin.domain.posts.domain.QPosts.posts

@Repository
class PostsQueryRepository (
    val queryFactory:JPAQueryFactory
    ) {
    fun findAllDecs() : List<Posts> {
        return queryFactory.selectFrom(posts)
            .orderBy(posts.id.desc())
            .fetch();
    }
}
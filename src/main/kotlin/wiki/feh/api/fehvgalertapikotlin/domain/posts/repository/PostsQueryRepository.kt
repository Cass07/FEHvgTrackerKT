package wiki.feh.api.fehvgalertapikotlin.domain.posts.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import wiki.feh.api.fehvgalertapikotlin.domain.posts.domain.Posts
import wiki.feh.api.fehvgalertapikotlin.domain.posts.domain.QPosts.posts
import wiki.feh.api.fehvgalertapikotlin.domain.posts.dto.PostsGetWithPicDto
import wiki.feh.api.fehvgalertapikotlin.domain.user.domain.QUser.user

@Repository
class PostsQueryRepository(
    val queryFactory: JPAQueryFactory
) {
    fun findAllDecs(): List<Posts> {
        return queryFactory.selectFrom(posts)
            .orderBy(posts.id.desc())
            .fetch();
    }

    fun findByTitle(title: String): List<Posts> {
        return queryFactory.selectFrom(posts)
            .where(posts.title.eq(title))
            .fetch()
    }

    fun getPostsWithPicture(id: Long): List<PostsGetWithPicDto> {
        return queryFactory
            .select(Projections.constructor(PostsGetWithPicDto::class.java,
                posts.id, posts.title, posts.content, posts.author, user.picture, user.name, posts.createDate, posts.modifiedDate))
            .from(posts)
            .innerJoin(user).on(posts.author.eq(user.email))
            .where(posts.id.eq(id))
            .fetch()
    }
}
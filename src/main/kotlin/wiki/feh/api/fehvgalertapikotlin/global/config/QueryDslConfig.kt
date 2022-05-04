package wiki.feh.api.fehvgalertapikotlin.global.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Configuration
open class QueryDslConfig {
    @PersistenceContext
    lateinit var entityManager:EntityManager

    @Bean
    fun jpaQueryFactory() :JPAQueryFactory{
        return JPAQueryFactory(entityManager)
    }
}
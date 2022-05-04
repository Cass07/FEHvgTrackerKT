package wiki.feh.api.fehvgalertapikotlin.domain.vginfo.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.domain.QVgInfo.vgInfo
import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.domain.VgInfo

@Repository
class VgInfoQueryRepository (val queryFactory: JPAQueryFactory) {

    fun getLatestVgInfo(): VgInfo? {
        return queryFactory
            .selectFrom(vgInfo)
            .orderBy(vgInfo.vgNumber.desc())
            .fetchFirst()
    }

    fun findAllDecs(): List<VgInfo> {
        return queryFactory
            .selectFrom(vgInfo)
            .orderBy(vgInfo.id.desc())
            .fetch()
    }

    fun findByVgnumber(vgNumber: Int): VgInfo? {
        return queryFactory
            .selectFrom(vgInfo)
            .where(vgInfo.vgNumber.eq(vgNumber))
            .fetchFirst()
    }

}
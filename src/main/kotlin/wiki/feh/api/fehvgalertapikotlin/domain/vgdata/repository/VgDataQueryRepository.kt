package wiki.feh.api.fehvgalertapikotlin.domain.vgdata.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import wiki.feh.api.fehvgalertapikotlin.domain.vgdata.domain.QVgData.vgData
import wiki.feh.api.fehvgalertapikotlin.domain.vgdata.domain.VgData

@Repository
class VgDataQueryRepository(val queryFactory: JPAQueryFactory) {

    //특정 vgnumber, roundnumber, tourIndex 로 조회되는 제일 최신의 vgdata
    fun getLatestVgDatabyNumRoundTour(vgNumber: Int, roundNumber: Int, tournamentIndex: Int): VgData? {
        return queryFactory
            .selectFrom(vgData)
            .where(vgData.vgNumber.eq(vgNumber), vgData.roundNumber.eq(roundNumber), vgData.tournamentIndex.eq(tournamentIndex))
            .orderBy(vgData.timeIndex.desc())
            .fetchFirst()
    }

    //특정 vgNumber로 조회되는 전체 라운드의 첫번쨰 vgdata 리스트 (초동 데이터 출력용)
    fun getFirstVgDataListbyVgNumber(vgNumber: Int): List<VgData>? {
        return queryFactory
            .selectFrom(vgData)
            .where(vgData.timeIndex.eq(1), vgData.vgNumber.eq(vgNumber))
            .orderBy(vgData.roundNumber.desc(), vgData.tournamentIndex.asc())
            .fetch()
    }

    //특정 vgnumber, roundnumber, tourindex로 조회되는 제일 처음의 vgdata
    fun getfirstVgDatabyNumRoundTour(vgNumber: Int, roundNumber: Int, tournamentIndex: Int): VgData? {
        return queryFactory
            .selectFrom(vgData)
            .where(vgData.vgNumber.eq(vgNumber), vgData.roundNumber.eq(roundNumber), vgData.tournamentIndex.eq(tournamentIndex))
            .orderBy(vgData.timeIndex.asc())
            .fetchFirst()
    }

    //특정 vgnumber로 조회되는 종료된 라운드의 마지막 vgdata 리스트 (결과표 출력용)
    fun getLatestVgDataListbyVgNumber(vgNumber: Int): List<VgData>? {
        return queryFactory
            .selectFrom(vgData)
            .where(vgData.timeIndex.eq(45), vgData.vgNumber.eq(vgNumber))
            .orderBy(vgData.roundNumber.desc(), vgData.tournamentIndex.asc())
            .fetch()
    }

    //특정 vgnumber, round로 조회되는 제일 최신의 vgdata리스트 4~1개(라운드따라) (현재 상황표 출력용)
    fun getNowtimeVgDataListbyVgNumberRound(vgNumber: Int, roundNumber: Int): List<VgData>? {
        val limitNum = intArrayOf(1, 4, 2, 1)
        return queryFactory
            .selectFrom(vgData)
            .where(vgData.vgNumber.eq(vgNumber), vgData.roundNumber.eq(roundNumber))
            .orderBy(vgData.timeIndex.desc(), vgData.tournamentIndex.asc())
            .limit(limitNum[roundNumber].toLong())
            .fetch()
    }

    //특정 vgnumber, roundnumber, tourindex로 조회되는 vgdata 리스트
    fun getVgDataListbyNumRoundTour(vgNumber: Int, roundNumber: Int, tournamentIndex: Int): List<VgData>? {
        return queryFactory
            .selectFrom(vgData)
            .where(vgData.vgNumber.eq(vgNumber), vgData.roundNumber.eq(roundNumber), vgData.tournamentIndex.eq(tournamentIndex))
            .orderBy(vgData.timeIndex.asc())
            .fetch()
    }
}
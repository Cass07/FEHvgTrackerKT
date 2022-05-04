package wiki.feh.api.fehvgalertapikotlin.domain.vgdata.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wiki.feh.api.fehvgalertapikotlin.domain.vgdata.domain.VgData
import wiki.feh.api.fehvgalertapikotlin.domain.vgdata.dto.VgDataGetDto
import wiki.feh.api.fehvgalertapikotlin.domain.vgdata.dto.VgDataResultGetDto
import wiki.feh.api.fehvgalertapikotlin.domain.vgdata.dto.VgDataSaveDto
import wiki.feh.api.fehvgalertapikotlin.domain.vgdata.repository.VgDataQueryRepository
import wiki.feh.api.fehvgalertapikotlin.domain.vgdata.repository.VgDataRepository
import java.util.stream.Collectors

@Service
class VgDataService (
    val vgDataRepository: VgDataRepository,
    val vgDataQueryRepository: VgDataQueryRepository
    ) {

    @Transactional
    fun save(vgDataSaveDto: VgDataSaveDto): Long? {
        return vgDataRepository.save(vgDataSaveDto.toEntity()).id
    }

    @Transactional(readOnly = true)
    fun getVgDataListbyNumRoundTour(vgNumber: Int, roundNumber: Int, tournamentIndex: Int): List<VgDataGetDto>? {
        return vgDataQueryRepository.getVgDataListbyNumRoundTour(vgNumber, roundNumber, tournamentIndex)?.map { entity -> VgDataGetDto(entity) }
    }

    @Transactional(readOnly = true)
    fun getLatestVgDatabyNumRoundTour(vgNumber: Int, roundNumber: Int, tournamentIndex: Int): VgDataGetDto? {
        vgDataQueryRepository.getLatestVgDatabyNumRoundTour(vgNumber, roundNumber, tournamentIndex)?.let{return VgDataGetDto(it)}
        return null
    }

    @Transactional(readOnly = true)
    fun getFirstVgDatabyNumRoundTour(vgNumber: Int, roundNumber: Int, tournamentIndex: Int): VgDataGetDto? {
        val entity: VgData? = vgDataQueryRepository.getfirstVgDatabyNumRoundTour(vgNumber, roundNumber, tournamentIndex)?. let{return VgDataGetDto(it)}
        return null
    }

    //초동 데이터 출력용 전체 라운드 5시 데이터
    @Transactional(readOnly = true)
    fun getFirstVgDataListbyVgNumber(vgNumber: Int): List<VgDataGetDto>? {
        return vgDataQueryRepository.getFirstVgDataListbyVgNumber(vgNumber)?.map { entity -> VgDataGetDto(entity) }
    }

    @Transactional(readOnly = true)
    fun getFirstVgDataResultListbyVgNumber(vgNumber: Int): List<VgDataResultGetDto>? {
        return vgDataQueryRepository.getFirstVgDataListbyVgNumber(vgNumber)?.map {entity ->  VgDataResultGetDto(entity) }
    }

    //결과값 출력용 전체 라운드 결과 데이터
    @Transactional(readOnly = true)
    fun getLatestVgDataListbyVgNumber(vgNumber: Int): List<VgDataResultGetDto>? {
        return vgDataQueryRepository.getLatestVgDataListbyVgNumber(vgNumber)?.map { entity -> VgDataResultGetDto(entity) }
    }

    //현재상황값 출력용 특정 라운드 제일 최신시간의 전체데이터
    @Transactional(readOnly = true)
    fun getNowtimeVgDataListbyVgNumberRound(vgNumber: Int, roundNumber: Int): List<VgDataGetDto>? {
        return vgDataQueryRepository.getNowtimeVgDataListbyVgNumberRound(vgNumber, roundNumber)?.map { entity -> VgDataGetDto(entity) }
    }
}
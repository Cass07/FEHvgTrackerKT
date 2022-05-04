package wiki.feh.api.fehvgalertapikotlin.domain.vginfo.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.domain.VgInfo
import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.dto.VgInfoGetDropdownDto
import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.dto.VgInfoGetDto
import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.dto.VgInfoSaveRequestDto
import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.repository.VgInfoQueryRepository
import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.repository.VgInfoRepository
import java.util.stream.Collectors

@Service
class VgInfoService(
    val vgInfoRepository: VgInfoRepository,
    val vgInfoQueryRepository: VgInfoQueryRepository) {

    @Transactional(readOnly = true)
    fun findbyId(id: Long): VgInfoGetDto? {
        vgInfoRepository.findByIdOrNull(id)?.let { return VgInfoGetDto(it) }
        return null
    }

    @Transactional(readOnly = true)
    fun findbyVgnumber(vgNumber: Int): VgInfoGetDto? {
        vgInfoQueryRepository.findByVgnumber(vgNumber)?.let { return VgInfoGetDto(it) }
        return null
    }

    @Transactional(readOnly = true)
    fun getLatestVgInfo(): VgInfoGetDto? {
        val entity: VgInfo? = vgInfoQueryRepository.getLatestVgInfo()?.let { return VgInfoGetDto(it) }
        return null
    }

    @Transactional(readOnly = true)
    fun findAllDesc(): List<VgInfoGetDto> {
        return vgInfoQueryRepository.findAllDecs().map { entity -> VgInfoGetDto(entity) }
    }

    @Transactional(readOnly = true)
    fun findAllDescDropdown(): List<VgInfoGetDropdownDto>? {
        val vgInfoGetDropdownDtos: MutableList<VgInfoGetDropdownDto> = vgInfoQueryRepository.findAllDecs().map { entity -> VgInfoGetDropdownDto(entity) } as MutableList<VgInfoGetDropdownDto>
        vgInfoGetDropdownDtos.add(VgInfoGetDropdownDto(-1L, "신규 데이터 추가"))
        vgInfoGetDropdownDtos.add(0, VgInfoGetDropdownDto(0L, "투표대전 리스트"))
        return vgInfoGetDropdownDtos
    }

    @Transactional
    fun save(vgInfoSaveRequestDto: VgInfoSaveRequestDto): Long? {
        return vgInfoRepository.save(vgInfoSaveRequestDto.toEntity()).id
    }

    @Transactional
    fun update(id: Long, vgInfoSaveRequestDto: VgInfoSaveRequestDto): Long? {
        vgInfoRepository.findByIdOrNull(id)?. let {
            it.update(vgNumber = vgInfoSaveRequestDto.vgNumber, vgTitle = vgInfoSaveRequestDto.vgTitle, vgStartDate = vgInfoSaveRequestDto.vgStartDate,
            team1Id = vgInfoSaveRequestDto.team1Id, team2Id = vgInfoSaveRequestDto.team2Id, team3Id = vgInfoSaveRequestDto.team3Id, team4Id = vgInfoSaveRequestDto.team4Id,
            team5Id = vgInfoSaveRequestDto.team5Id, team6Id = vgInfoSaveRequestDto.team6Id, team7Id = vgInfoSaveRequestDto.team7Id, team8Id = vgInfoSaveRequestDto.team8Id)
            return id
        }
        return null
    }
}
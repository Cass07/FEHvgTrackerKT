package wiki.feh.api.fehvgalertapikotlin.domain.global.dto

import wiki.feh.api.fehvgalertapikotlin.domain.vgdata.dto.VgDataGetDto
import wiki.feh.api.fehvgalertapikotlin.domain.vgdata.dto.VgDataResultGetDto
import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.dto.VgInfoGetDto

class VgViewDto(
    val vgInfoEntity: VgInfoGetDto?,
    val currentRoundVgdata: List<VgDataGetDto>?,
    val round1Vgdata: List<VgDataResultGetDto>?,
    val round2Vgdata: List<VgDataResultGetDto>?,
    val round3Vgdata: List<VgDataResultGetDto>?,
    val viewModel: Map<String, String>,
    val viewString: String
) {
}
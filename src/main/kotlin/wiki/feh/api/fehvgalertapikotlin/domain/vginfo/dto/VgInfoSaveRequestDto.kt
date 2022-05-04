package wiki.feh.api.fehvgalertapikotlin.domain.vginfo.dto

import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.domain.VgInfo
import java.time.LocalDate

class VgInfoSaveRequestDto(
    val vgNumber: Int,
    val vgTitle: String,
    val vgStartDate: LocalDate,
    val team1Id: String,
    val team2Id: String,
    val team3Id: String,
    val team4Id: String,
    val team5Id: String,
    val team6Id: String,
    val team7Id: String,
    val team8Id: String
) {

    fun toEntity(): VgInfo {
        return VgInfo(vgNumber = vgNumber, vgTitle = vgTitle, vgStartDate = vgStartDate, team1Id = team1Id, team2Id = team2Id, team3Id = team3Id,
            team4Id = team4Id, team5Id = team5Id, team6Id = team6Id, team7Id = team7Id, team8Id = team8Id)
    }
}
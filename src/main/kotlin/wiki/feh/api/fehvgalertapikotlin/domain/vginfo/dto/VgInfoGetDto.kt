package wiki.feh.api.fehvgalertapikotlin.domain.vginfo.dto

import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.domain.VgInfo
import java.time.LocalDate

class VgInfoGetDto(entity: VgInfo) {

    val id: Long = entity.id
    val vgNumber: Int = entity.vgNumber
    val vgTitle: String = entity.vgTitle
    val vgStartDate: LocalDate = entity.vgStartDate

    val team1Id: String = entity.team1Id
    val team2Id: String = entity.team2Id
    val team3Id: String = entity.team3Id
    val team4Id: String = entity.team4Id
    val team5Id: String = entity.team5Id
    val team6Id: String = entity.team6Id
    val team7Id: String = entity.team7Id
    val team8Id: String = entity.team8Id

    fun getTeamIdbyIndex(index: Int): String? {
        return when (index) {
            1 -> this.team1Id
            2 -> this.team2Id
            3 -> this.team3Id
            4 -> this.team4Id
            5 -> this.team5Id
            6 -> this.team6Id
            7 -> this.team7Id
            8 -> this.team8Id
            else -> null
        }
    }
}
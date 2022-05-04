package wiki.feh.api.fehvgalertapikotlin.domain.vgdata.dto

import wiki.feh.api.fehvgalertapikotlin.domain.vgdata.domain.VgData
import kotlin.math.pow

class VgDataSaveDto(
    val vgNumber: Int,
    val team1Score: String,
    val team2Score: String,
    val team1Index: Int,
    val team2Index: Int,
    val roundNumber: Int,
    val tournamentIndex: Int,
    val timeIndex: Int
) {

    fun toEntity(): VgData {
        return VgData(vgNumber = vgNumber, team1Score = team1Score.replace(",", ""),
            team2Score = team2Score.replace(",", ""), team1Index = team1Index, team2Index = team2Index,
            roundNumber = roundNumber, tournamentIndex = tournamentIndex, timeIndex = timeIndex)
    }
}
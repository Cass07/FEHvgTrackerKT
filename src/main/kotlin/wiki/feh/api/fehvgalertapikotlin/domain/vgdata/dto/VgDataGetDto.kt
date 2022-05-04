package wiki.feh.api.fehvgalertapikotlin.domain.vgdata.dto

import wiki.feh.api.fehvgalertapikotlin.domain.vgdata.domain.VgData
import kotlin.math.pow

class VgDataGetDto(entity: VgData) {
    val id: Long = entity.id
    val vgNumber: Int = entity.vgNumber
    val team1Score: String = entity.team1Score
    val team2Score: String = entity.team2Score

    val team1Index: Int = entity.team1Index
    val team2Index: Int = entity.team2Index

    val tournamentIndex: Int = entity.tournamentIndex

    val timeIndex: Int = entity.timeIndex

    val losing: Int = this.calcLosing()
    val team1Rate: String = this.calcTeam1Rate()
    val team2Rate: String = this.calcTeam2Rate()

    private fun calcLosing(): Int {
        return if (this.floor(team1Score.toLong() / (team2Score.toDouble()), 4) > 1.01) {
            2
        } else if (this.floor(team2Score.toLong() / (team1Score.toDouble()), 4) > 1.01) {
            1
        } else {
            0
        }
    }

    private fun floor(number: Double, dec: Int): Double {
        val pow: Double = 10.0.pow(dec.toDouble())
        return kotlin.math.floor(number * pow) / pow
    }

    private fun calcTeam1Rate() : String {
        val rate: Double = team1Score.toLong() / team2Score.toDouble()
        return String.format("%.3f", rate)
    }

    private fun calcTeam2Rate() : String {
        val rate: Double = team2Score.toLong() / team1Score.toDouble()
        return String.format("%.3f", rate)
    }
}
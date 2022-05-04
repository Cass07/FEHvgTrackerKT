package wiki.feh.api.fehvgalertapikotlin.domain.vgdata.domain

import javax.persistence.*

@Entity
class VgData (
    @Column(nullable = false) val vgNumber: Int,
    @Column(length = 20, nullable = false) val team1Score: String,
    @Column(length = 20, nullable = false) val team2Score: String,
    val team1Index: Int,
    val team2Index: Int,
    val roundNumber: Int,
    val tournamentIndex: Int,
    val timeIndex: Int
    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}
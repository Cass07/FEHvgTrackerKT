package wiki.feh.api.fehvgalertapikotlin.domain.vginfo.domain

import java.time.LocalDate
import javax.persistence.*

@Entity
class VgInfo(
    vgNumber: Int,
    vgTitle: String,
    vgStartDate: LocalDate,
    team1Id: String,
    team2Id: String,
    team3Id: String,
    team4Id: String,
    team5Id: String,
    team6Id: String,
    team7Id: String,
    team8Id: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(nullable = false)
    var vgNumber: Int = vgNumber
        protected set

    @Column(nullable = false)
    var vgTitle: String = vgTitle
        protected set

    @Column(nullable = false)
    var vgStartDate: LocalDate = vgStartDate
        protected set

    @Column(length = 50)
    var team1Id: String = team1Id
    protected set

    @Column(length = 50)
    var team2Id: String = team2Id
        protected set

    @Column(length = 50)
    var team3Id: String = team3Id
        protected set

    @Column(length = 50)
    var team4Id: String = team5Id
        protected set

    @Column(length = 50)
    var team5Id: String = team5Id
        protected set

    @Column(length = 50)
    var team6Id: String = team6Id
        protected set

    @Column(length = 50)
    var team7Id: String = team7Id
        protected set

    @Column(length = 50)
    var team8Id: String = team8Id
        protected set


    fun update(vgNumber: Int, vgTitle: String, vgStartDate: LocalDate, team1Id: String, team2Id: String, team3Id: String, team4Id: String, team5Id: String,
    team6Id: String, team7Id: String, team8Id: String) {
        this.vgNumber = vgNumber
        this.vgTitle = vgTitle
        this.vgStartDate = vgStartDate
        this.team1Id = team1Id
        this.team2Id = team2Id
        this.team3Id = team3Id
        this.team4Id = team4Id
        this.team5Id = team5Id
        this.team6Id = team6Id
        this.team7Id = team7Id
        this.team8Id = team8Id
    }

}
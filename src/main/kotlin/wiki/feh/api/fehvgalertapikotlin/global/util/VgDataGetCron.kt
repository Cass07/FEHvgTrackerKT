package wiki.feh.api.fehvgalertapikotlin.global.util

import org.jsoup.Jsoup
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import wiki.feh.api.fehvgalertapikotlin.domain.posts.dto.PostsSaveRequestDto
import wiki.feh.api.fehvgalertapikotlin.domain.posts.service.PostsService
import wiki.feh.api.fehvgalertapikotlin.domain.vgdata.dto.VgDataSaveDto
import wiki.feh.api.fehvgalertapikotlin.domain.vgdata.service.VgDataService
import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.dto.VgInfoGetDto
import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.service.VgInfoService
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Component
class VgDataGetCron(
    val vgDataService: VgDataService,
    val vgInfoService: VgInfoService,
    val postsService: PostsService,
) {

    var currentVgInfo: VgInfoGetDto? = null

    @Scheduled(cron = "0 5 * * * *") //* 5 * * * * 매 시 5분, 15분에 실행 (한시간에 한번만 실행할까?)
    fun GetVgData() {
        initCurrentVgInfo(LocalDateTime.now())
        if (currentVgInfo != null) {
            val currentTime = LocalDateTime.now()
            val timeDiffAll = getTimeDiff(currentVgInfo!!.vgStartDate, currentTime)
            //라운드 구하기
            val roundNumber = getRoundbyTimediff(timeDiffAll)
            val timeDiff = getTimeDiff(currentVgInfo!!.vgStartDate, currentTime, roundNumber)
            saveVgdatabyRoundTimediffVgnum(roundNumber, timeDiff, currentVgInfo!!.vgNumber)
            //라운드와 시간인덱스 가지고 저장할 데이터 리스트 색출해서 저장 (1, 2라운드 시간인덱스 45 46 47은 휴식해야함 다음라운드 0에서 결과 받아오게됨)
            if (timeDiffAll == 141L) {
                currentVgInfo = null
            }
            //마지막 데이터라면 (시간인덱스 141) 종료처리하기 (null값으로)
        }
    }

    private fun initCurrentVgInfo(currentTime: LocalDateTime) {
        //currentvginfo가 null이면 새로 조회해서 값을 저장하고 값이 잇다면 걍넘김 (vg가 끝나면 null값으로 다시 초기화함)
        if (currentVgInfo == null) {
            currentVgInfo = vgInfoService.getLatestVgInfo()
            if (currentVgInfo != null) {
                val diff = getTimeDiff(currentVgInfo!!.vgStartDate, currentTime)
                if (diff <= 0 || diff > 141) //시작날짜 이전이거나 시간날짜로부터 마지막 라운드가 끝낫다면 null로함.
                {
                    if (diff <= 0 && diff > -5) {
                        postsService.save(PostsSaveRequestDto("로그 : null", "없음", "kjh95828@gmail.com"))
                    }
                    currentVgInfo = null
                }
            }
        }
    }

    private fun saveVgdatabyRoundTimediffVgnum(round: Int, timeDiff: Long, vgNumber: Int): Boolean {
        var timeDiffReal = timeDiff.toInt()
        if (round == 1 && timeDiff == 0L) {
            postsService.save(PostsSaveRequestDto("로그 : $vgNumber", "1라운드 시간차 0입니다.", "kjh95828@gmail.com"))
            return false
        }
        return if ((round == 1 || round == 2) && timeDiff >= 45 && timeDiff <= 47) {
            postsService.save(PostsSaveRequestDto("로그 : $vgNumber", round.toString() + "라운드 시간차 ${timeDiff}입니다.", "kjh95828@gmail.com"))
            //계산중일때는 <p>태그 하나에 계산중입니다 이거만써잇음
            false
        } else {
            val data = getMapListbyCrawl(vgNumber)
            val dataSize = data.size
            if (dataSize == 4) {
                if (timeDiff == 48L) {
                    timeDiffReal = 45
                }
                for (i in 0..3) {
                    saveFromMap(data[i], round, timeDiffReal, vgNumber, i + 1)
                }
            } else if (dataSize == 6) {
                if (timeDiff == 48L) {
                    timeDiffReal = 45
                }
                if (round == 1) {
                    for (i in 2..5) {
                        saveFromMap(data[i], round, timeDiffReal, vgNumber, i - 1)
                    }
                } else {
                    for (i in 0..1) {
                        saveFromMap(data[i], round, timeDiffReal, vgNumber, i + 1)
                    }
                }
            } else if (dataSize == 7) {
                if (round == 2 && timeDiff == 48L) {
                    timeDiffReal = 45
                    for (i in 1..2) {
                        saveFromMap(data[i], round, timeDiffReal, vgNumber, i)
                    }
                } else {
                    saveFromMap(data[0], round, timeDiffReal, vgNumber, 1)
                }
            }
            true
        }
    }

    private fun saveFromMap(data: Map<String, String>, round: Int, timeDiff: Int, vgNumber: Int, tournamentIndex: Int) {
        if(!data["team1Score"].isNullOrEmpty() && !data["team2Score"].isNullOrEmpty() && !data["team1Index"].isNullOrEmpty() && !data["team2Index"].isNullOrEmpty())
        {
            vgDataService.save(VgDataSaveDto(vgNumber, data.getValue("team1Score"), data.getValue("team2Score"),
                data.getValue("team1Index").toInt(), data.getValue("team2Index").toInt(),
                round, tournamentIndex, timeDiff))
        }

    }

    private fun getMapListbyCrawl(vgNumber: Int): List<Map<String, String>> {
        val doc = Jsoup.connect("https://support.fire-emblem-heroes.com/voting_gauntlet/tournaments/$vgNumber").get()
        val tb_ = doc.select(".tournaments-battle")
        val output: MutableList<Map<String, String>> = ArrayList()
        var toPost = ""
        for (tb in tb_) {
            //tournament battle마다 한 배틀에 참여하는 두 팀의 div가 들어있다
            //div 안의 p 요소에 이름과 점수 데이터가 들어 있다.

            //TODO :: VG 라운드 변경날짜 16~17시일때, 현재라운드 데이터는 없는상태인데 요소는 존재하는지 (즉 0으로 되어잇는지 아예없는지?) 확인필요
            // 이거 확인해본결과 16~17시에 현재라운드 요소는 존재하고 대신 표수 <p> 태그엔 값없었음
            val team1_div = tb.children().first()
            val team1_number = team1_div!!.attr("class").replace("tournaments-art-left art-$vgNumber-", "")
                .replace("-behind", "").replace("-normal", "")
            val team1_name = team1_div.select("p")[0].text()
            val team1_score = team1_div.select("p")[1].text()
            val team2_div = tb.children()[1]
            val team2_number = team2_div.attr("class").replace("tournaments-art-right art-$vgNumber-", "")
                .replace("-behind", "").replace("-normal", "")
            val team2_name = team2_div.select("p")[0].text()
            val team2_score = team2_div.select("p")[1].text()
            toPost = (toPost + "<br>" + team1_number + team1_name + team1_score + "<br>"
                + team2_number + team2_name + team2_score)
            output.add(mapOf("team1Index" to team1_number, "team1Score" to team1_score.replace(",", ""),
                "team2Index" to team2_number, "team2Score" to team2_score.replace(",", "")))
        }
        postsService.save(PostsSaveRequestDto("로그 : $vgNumber 수집완료", toPost, "kjh95828@gmail.com"))
        return output
    }

    private fun getRoundbyTimediff(timeDiff: Long): Int {
        return (timeDiff.toInt() - 1) / 48 + 1
    }

    private fun getTimeDiff(vgStartDate: LocalDate, currentTime: LocalDateTime): Long {
        return ChronoUnit.HOURS.between(vgStartDate.atTime(16, 0), currentTime)
    }

    private fun getTimeDiff(vgStartDate: LocalDate, currentTime: LocalDateTime, round: Int): Long {
        return ChronoUnit.HOURS.between(vgStartDate.plusDays((round * 2 - 2).toLong()).atTime(16, 0), currentTime)
    }

}
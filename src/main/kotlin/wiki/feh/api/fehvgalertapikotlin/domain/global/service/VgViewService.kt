package wiki.feh.api.fehvgalertapikotlin.domain.global.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import wiki.feh.api.fehvgalertapikotlin.domain.global.dto.VgViewDto
import wiki.feh.api.fehvgalertapikotlin.domain.vgdata.dto.VgDataGetDto
import wiki.feh.api.fehvgalertapikotlin.domain.vgdata.dto.VgDataResultGetDto
import wiki.feh.api.fehvgalertapikotlin.domain.vgdata.service.VgDataService
import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.dto.VgInfoGetDto
import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.service.VgInfoService
import java.time.format.DateTimeFormatter

@Service
class VgViewService(
    val vgInfoService: VgInfoService,
    val vgDataService: VgDataService
) {

    @Transactional(readOnly = true)
    fun getVgMainbyid(id: Long): VgViewDto? {
        //vgnum이 -1이라면 제일 최신의 VgInfodata를 조회한다.
        var viewModel: MutableMap<String, String> = HashMap()
        var currentRoundVgdata: List<VgDataGetDto>? = null
        var round1Vgdata: List<VgDataResultGetDto>? = null
        var round2Vgdata: List<VgDataResultGetDto>? = null
        var round3Vgdata: List<VgDataResultGetDto>? = null
        val vginfoEntity: VgInfoGetDto? = if (id == -1L) {
            vgInfoService.getLatestVgInfo()
        } else {
            vgInfoService.findbyId(id)
        }
        if (vginfoEntity == null) {
            return VgViewDto(viewString = "posts-error", viewModel = mapOf("errorMessage" to "오류가 발생했습니다.<br>해당 투표대전 조회되지 않음."),
                vgInfoEntity = null, round1Vgdata = null, round2Vgdata = null, round3Vgdata = null, currentRoundVgdata = null)
        }

        putHeroNamdIdtoMapByVginfo(viewModel, vginfoEntity)

        val latestVgNumber: Int = vginfoEntity.vgNumber
        val resultVgDataList: List<VgDataResultGetDto>? = vgDataService.getLatestVgDataListbyVgNumber(latestVgNumber)
        val round: Int = when (resultVgDataList?.size) {
            0 -> 1
            4 -> 2
            6 -> 3
            7 -> 0
            else -> -1
        }
        if (round == -1) {
            return VgViewDto(viewString = "posts-error", viewModel = mapOf("errorMessage" to "오류가 발생했습니다.<br>해당 투표대전의 종료된 라운드 데이터 값에 오류가 있음."),
                vgInfoEntity = null, round1Vgdata = null, round2Vgdata = null, round3Vgdata = null, currentRoundVgdata = null)
        }
        val currentVgDataList: List<VgDataGetDto>? = vgDataService.getNowtimeVgDataListbyVgNumberRound(latestVgNumber, round)
        if (currentVgDataList.isNullOrEmpty()) {
            //아예 시작안햇거나(라운드1), 이전라운드 종료되엇고 현재라운드 시작안햇거나, 모든 라운드 종료됨
            if (round == 1) {
                //아예 시작안함 1라운드 예정 대진표만 출력
                //데이터
                viewModel["round1StartNot"] = "1라운드 대진표"
            }
        } else {
            //진행중인 라운드 데이터 넘겨줌
            viewModel["currentRoundVgTitle"] = round.toString() + "라운드 진행 중"
            currentRoundVgdata = currentVgDataList
        }

        //해당 라운드면 어쨌든 그 전 라운드는 종료된상태이니 종료라운드 데이터 넘겨줌
        if (round == 2) {
            viewModel["round1VgTitle"] = "1라운드 결과"
            round1Vgdata = resultVgDataList
        } else if (round == 3) {
            viewModel["round2VgTitle"] = "2라운드 결과"
            round2Vgdata = resultVgDataList?.subList(0, 2)
            viewModel["round1VgTitle"] = "1라운드 결과"
            round1Vgdata = resultVgDataList?.subList(2, 6)
        } else if (round == 0) {
            viewModel["round3VgTitle"] = "3라운드 결과"
            round3Vgdata = resultVgDataList?.subList(0, 1)
            viewModel["round2VgTitle"] = "2라운드 결과"
            round2Vgdata = resultVgDataList?.subList(1, 3)
            viewModel["round1VgTitle"] = "1라운드 결과"
            round1Vgdata = resultVgDataList?.subList(3, 7)
        }
        viewModel["vgStartDateTimeStr"] = vginfoEntity.vgStartDate.atTime(16, 0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        viewModel["vgEndDateTimeStr"] = vginfoEntity.vgStartDate.atTime(13, 0).plusDays(6).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        return VgViewDto(viewString = "vg-data-main", viewModel = viewModel, vgInfoEntity = vginfoEntity, round1Vgdata = round1Vgdata,
            round2Vgdata = round2Vgdata, round3Vgdata = round3Vgdata, currentRoundVgdata = currentRoundVgdata)
    }

    @Transactional(readOnly = true)
    fun getVgFirstbyId(id: Long): VgViewDto? {
        var viewModel: MutableMap<String, String> = HashMap()
        val vginfoEntity: VgInfoGetDto?
        val currentRoundVgdata: List<VgDataGetDto>? = null
        var round1Vgdata: List<VgDataResultGetDto>? = null
        var round2Vgdata: List<VgDataResultGetDto>? = null
        var round3Vgdata: List<VgDataResultGetDto>? = null
        vginfoEntity = if (id == -1L) {
            vgInfoService.getLatestVgInfo()
        } else {
            vgInfoService.findbyId(id)
        }
        if (vginfoEntity == null) {
            return VgViewDto(viewString = "posts-error", viewModel = mapOf("errorMessage" to "오류가 발생했습니다.<br>해당 투표대전 조회되지 않음."),
                vgInfoEntity = null, round1Vgdata = null, round2Vgdata = null, round3Vgdata = null, currentRoundVgdata = null)
        }

        putHeroNamdIdtoMapByVginfo(viewModel, vginfoEntity)

        viewModel["header_title"] = "투표대전 점수 트래커 - 초동 데이터"

        val firstVgNumber: Int = vginfoEntity.vgNumber
        val firstVgDataList: List<VgDataResultGetDto>? = vgDataService.getFirstVgDataResultListbyVgNumber(firstVgNumber)
        val round: Int = when (firstVgDataList?.size) {
            4 -> 1
            6 -> 2
            7 -> 3
            0 -> 0
            else -> -1
        }
        if (round == -1) {
            return VgViewDto(viewString = "posts-error", viewModel = mapOf("errorMessage" to "오류가 발생했습니다.<br>해당 투표대전의 초동 데이터 값에 오류가 있음."),
                vgInfoEntity = null, round1Vgdata = null, round2Vgdata = null, round3Vgdata = null, currentRoundVgdata = null)
        }
        if (round == 1) {
            viewModel["round1VgTitle"] = "1라운드 5시 데이터"
            round1Vgdata = firstVgDataList
        } else if (round == 2) {
            viewModel["round2VgTitle"] = "2라운드 5시 데이터"
            round2Vgdata = firstVgDataList?.subList(0, 2)
            viewModel["round1VgTitle"] = "1라운드 5시 데이터"
            round1Vgdata = firstVgDataList?.subList(2, 6)
        } else if (round == 3) {
            viewModel["round3VgTitle"] = "3라운드 5시 데이터"
            round3Vgdata = firstVgDataList?.subList(0, 1)
            viewModel["round2VgTitle"] = "2라운드 5시 데이터"
            round2Vgdata = firstVgDataList?.subList(1, 3)
            viewModel["round1VgTitle"] = "1라운드 5시 데이터"
            round1Vgdata = firstVgDataList?.subList(3, 7)
        }
        viewModel["vgStartDateTimeStr"] = vginfoEntity.vgStartDate.atTime(16, 0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        viewModel["vgEndDateTimeStr"] = vginfoEntity.vgStartDate.atTime(13, 0).plusDays(6).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

        return VgViewDto(viewString = "vg-data-main", viewModel = viewModel, vgInfoEntity = vginfoEntity, round1Vgdata = round1Vgdata,
            round2Vgdata = round2Vgdata, round3Vgdata = round3Vgdata, currentRoundVgdata = currentRoundVgdata)
    }

    //vginfo에서 캐릭터 아이디랑 이름 데이터 받아서 model 데이터 저장하는 map에다 저장해줌
    private fun putHeroNamdIdtoMapByVginfo(map: MutableMap<String, String>, vgInfoEntity: VgInfoGetDto) {
        for (i in 1..8) {
            val tmp: List<String> = vgInfoEntity.getTeamIdbyIndex(i)!!.split("#")
            map["team" + i + "Id"] = tmp[0]
            map["team" + i + "Name"] = tmp[1]
        }
    }
}
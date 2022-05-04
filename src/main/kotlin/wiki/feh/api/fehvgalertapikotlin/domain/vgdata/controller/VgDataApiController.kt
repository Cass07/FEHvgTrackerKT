package wiki.feh.api.fehvgalertapikotlin.domain.vgdata.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import wiki.feh.api.fehvgalertapikotlin.domain.vgdata.dto.VgDataGetDto
import wiki.feh.api.fehvgalertapikotlin.domain.vgdata.service.VgDataService

@CrossOrigin(origins = arrayOf("http://localhost:3000")) //React 테스트용
@RestController
class VgDataApiController (
    val vgDataService: VgDataService
    ) {
    @GetMapping("/api/v1/vgdata/vgnum/{vgnum}/round/{round}/tournum/{tournum}")
    fun getAllList(@PathVariable vgnum: Int, @PathVariable round: Int, @PathVariable tournum: Int): List<VgDataGetDto>? {
        return vgDataService.getVgDataListbyNumRoundTour(vgnum, round, tournum)
    }

    @GetMapping("/api/v1/vgdata/vgnum/{vgnum}/round/{round}/tournum/{tournum}/first")
    fun getFirstDto(@PathVariable vgnum: Int, @PathVariable round: Int, @PathVariable tournum: Int): VgDataGetDto? {
        return vgDataService.getFirstVgDatabyNumRoundTour(vgnum, round, tournum)
    }

    @GetMapping("/api/v1/vgdata/vgnum/{vgnum}/round/{round}/tournum/{tournum}/latest")
    fun getLatestDto(@PathVariable vgnum: Int, @PathVariable round: Int, @PathVariable tournum: Int): VgDataGetDto? {
        return vgDataService.getLatestVgDatabyNumRoundTour(vgnum, round, tournum)
    }

    @GetMapping("/api/v1/vgdata/vgnum/{vgnum}/round/{round}/latest")
    fun getLatestDtoListbyVgnumRound(@PathVariable vgnum: Int, @PathVariable round: Int): List<VgDataGetDto>? {
        return vgDataService.getNowtimeVgDataListbyVgNumberRound(vgnum, round)
    }

    @GetMapping("/api/v1/vgdata/vgnum/{vgnum}/first")
    fun getFirstDtoListbyVgnum(@PathVariable vgnum: Int): List<VgDataGetDto>? {
        return vgDataService.getFirstVgDataListbyVgNumber(vgnum)
    }

    @GetMapping("/api/v1/vgdata/vgnum/{vgnum}/result")
    fun getLatestVgDataListbyVgNumber(@PathVariable vgnum: Int): List<VgDataGetDto>? {
        return vgDataService.getFirstVgDataListbyVgNumber(vgnum)
    }
}
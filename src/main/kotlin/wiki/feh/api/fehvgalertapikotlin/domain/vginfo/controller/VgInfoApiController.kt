package wiki.feh.api.fehvgalertapikotlin.domain.vginfo.controller

import org.springframework.web.bind.annotation.*
import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.dto.VgInfoGetDropdownDto
import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.dto.VgInfoGetDto
import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.dto.VgInfoSaveRequestDto
import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.service.VgInfoService

@RestController
class VgInfoApiController(
    val vgInfoService: VgInfoService
) {
    @GetMapping("/api/v1/vginfo/{id}")
    operator fun get(@PathVariable id: Long?): VgInfoGetDto? {
        return id?.let { vgInfoService.findbyId(it) }
    }

    @GetMapping("/api/v1/vginfo")
    fun getAll(): List<VgInfoGetDropdownDto>? {
        return vgInfoService.findAllDescDropdown()
    }

    @PutMapping("/api/v1/vginfo/{id}")
    fun update(@PathVariable id: Long, @RequestBody vgInfoSaveRequestDto: VgInfoSaveRequestDto?): Long? {
        return vgInfoSaveRequestDto?.let { vgInfoService.update(id, it) }
    }

    @PostMapping("/api/v1/vginfo")
    fun save(@RequestBody vgInfoSaveRequestDto: VgInfoSaveRequestDto?): Long? {
        return vgInfoSaveRequestDto?.let { vgInfoService.save(it) }
    }
}
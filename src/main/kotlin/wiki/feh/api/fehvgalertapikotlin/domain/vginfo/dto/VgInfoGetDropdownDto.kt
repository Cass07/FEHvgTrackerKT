package wiki.feh.api.fehvgalertapikotlin.domain.vginfo.dto

import wiki.feh.api.fehvgalertapikotlin.domain.vginfo.domain.VgInfo

class VgInfoGetDropdownDto {

    val id: Long
    val text: String

    constructor(entity: VgInfo) {
        this.id = entity.id
        this.text = entity.vgNumber.toString() + "회 : " + entity.vgTitle + " : " + entity.vgStartDate.toString()
    }

    constructor(id: Long, text: String) {
        this.id = id
        this.text = text
    }

}
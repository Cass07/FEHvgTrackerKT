package wiki.feh.api.fehvgalertapikotlin.domain.vgdata.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import wiki.feh.api.fehvgalertapikotlin.domain.vgdata.domain.VgData

@Repository
interface VgDataRepository : JpaRepository<VgData, Long> {
}